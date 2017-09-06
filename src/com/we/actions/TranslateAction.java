package com.we.actions;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.we.util.AddressUtil;
import com.we.util.Base64Util;
import com.we.util.Consts;
import com.we.util.PathUtils;
import com.we.util.ShellUtil;
import com.we.util.WavToMp3;
import com.we.service.SwitchService;
import com.we.util.WeproUtil;


@Controller
@RequestMapping(value = "/translate")
@Scope("prototype")
public class TranslateAction extends BaseAction {
    
    private static final Log logger = LogFactory.getLog(TranslateAction.class);
    /* 存储系统时间和ACCESS_TOKEN */
    private static Date date = null;// 当前系统时间
    private static String ACCESS_TOKEN = "";// 百度的ACCESS_TOKEN
    /* 谷歌API_KEY */
    private static final String API_KEY = "AIzaSyAuaQfjY8kxjNz8el0K9wlvkIq0xFT9H3A";
    /* 谷歌语音识别请求参数 */
    private static final String ENCODING = "FLAC";// 请求识别文件类型
    private static final Integer SAMPLERATE = 24000;// 音频文件的采样率
    /* 百度语音合成请求参数 */
    private static final String GRANT_TYPE = "client_credentials";
    private static final String CLIENT_ID = "VPh0vTFPZ9NLjoGHXaWotQlU";// 百度APP_KEY
    private static final String CLIENT_SECRET = "fce8db060c813f504e21a96ff027c2f9";// 百度APP_SCRET
    /* 谷歌接口地址 */
    private static final String TRANSLATION_URL = "https://translation.googleapis.com/language/translate/v2";
    private static final String SPEECH_URL = "https://speech.googleapis.com/v1beta1/speech:syncrecognize?key=";
    /* 百度语音合成 */
    private static final String TOKEN_URL = "https://openapi.baidu.com/oauth/2.0/token";
    private static final String VOICE_URL = "http://tsn.baidu.com/text2audio";
    /* MaryTTS接口地址 */
    private static final String MARYTTS_URL = "http://47.52.36.169:59125/process";
    //private static final String MARYTTS_URL = "http://localhost:59125/process";

    @Autowired
    private SwitchService switchService;

    /**
     * 获取谷歌文本翻译结果
     * @author Wang Hanqing
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public void getTranslate(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /* 初始化请求参数集合 */
        Map<String, Object> params = new HashMap<String, Object>();
        /* 获取请求参数 */
        String source = request.getParameter("source");
        String target = request.getParameter("target");
        String q = request.getParameter("q");

        /* 设置请求参数 key:API_KEY（必填） source:源语言（非必填）target:目标语言（必填）q:翻译文本（必填） */
        params.put("key", API_KEY); // 谷歌API_KEY
        // 谷歌可自动识别源语言，判断是否为空，若为空则不添加此参数
        if (!"".equals(source) && source != null) {
            params.put("source", source); // 源语言
        }
        params.put("target", target); // 源语言
        params.put("q", q); // 查询文本

        /* 请求谷歌接口，获取请求结果（JSON） */
        //String result = WeproUtil.net(TRANSLATION_URL, params, "GET");
        String result = WeproUtil.httpsRequest(TRANSLATION_URL, "POST", WeproUtil.urlencode(params));
        System.out.println("####################请求参数:" + params);
        System.out.println("####################谷歌翻译结果:" + result);
        /* 设置结果集合 */
        Map<String, Object> resultMap = new HashMap<String, Object>();

        /* 非空检查 */
        if (checkParamNull(result)) {
            resultMap = setResCode(Consts.EXCEPTION_CODE, "谷歌翻译返回结果为空",
                    Consts.ErrorMsg.MSG00001);
            setResponseEncrypt(resultMap, response);
            return;
        }
        
        /* 特殊符号处理 */
        result = result.replaceAll("&#39;", "'");
        
        /* 处理文本翻译结果集 */
        JSONObject obj = JSONObject.fromObject(result);
        JSONArray resultArray = obj.getJSONObject("data").getJSONArray(
                "translations");
        
        /* 获取翻译的文本 */
        String tex = obj.getJSONObject("data").getJSONArray(
                "translations").getJSONObject(0).getString("translatedText");
        
        /* 若支持语音合成，则返回语音文件，否则语音文件为null */
        String voiceFilePath = getVoice(target, tex, request, response);
        
        /* 设置返回结果 */
        resultMap.put("rescode", Consts.SUCCESS_CODE);
        resultMap.put("resmsg_cn", "谷歌翻译请求成功");
        resultMap.put("q", q);
        resultMap.put("data", resultArray);
        // 判断合成语音是否为空，如果为空则返回空字符串
        resultMap.put("voice_file", voiceFilePath);
        System.out.println("**********返回结果:" + resultMap);
        setResponseEncrypt(resultMap, response);
    }

    /**
     * 微信小程序语音识别
     * 传入识别的语音文件
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/getSpeech", method = RequestMethod.POST)
    public void getSpeech(@RequestParam("fileKey") MultipartFile[] fileKey, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /* 设置请求参数集合 */
        Map<String, Object> params = new HashMap<String, Object>();
        Map<String, Object> configParams = new HashMap<String, Object>();
        Map<String, Object> audioParams = new HashMap<String, Object>();
        Map<String, Object> translatParams = new HashMap<String, Object>();
        
        /* 设置结果集合 */
        Map<String, Object> resultMap = new HashMap<String, Object>();
        
        /* 获取请求参数 languageCode:语音翻译目标语言 target:语音识别目标语言 */
        String languageCode = request.getHeader("source");
        String target = request.getHeader("target");
        
        /* 设置请求文件转存地址并添加时间戳
         * 转存上传的文件以添加时间戳的格式保存到指定目录下 */
        String filePath =PathUtils.getWebRootUrl(request) + "/upload/" 
                + System.currentTimeMillis() + fileKey[0].getOriginalFilename();
        fileKey[0].transferTo(new File(filePath));    
        
        /* Linux下执行指定的shell脚本，转换文件类型到指定类型
         * 获取转换后文件的路径 */
        ShellUtil.executeCommands("sh /home/weblogic/war/GoogleService/Transcoding/converter.sh " + filePath + " flac ");
        String flacFilePath = filePath.replace("silk", "flac");
        
        /* 获取转换文件的base64加密字符串，用于传递参数 */
        String content = Base64Util.getBase64(flacFilePath).replaceAll("\n", "");

        /* 设置请求参数 encoding:音频编码格式（必填） sampleRate:采样率（必填） 
         * languageCode:源语言（非必填）默认为en content:base64编码的音频文件（必填） */
        configParams.put("encoding", ENCODING);
        configParams.put("sampleRate", SAMPLERATE);
        configParams.put("languageCode", languageCode);
        audioParams.put("content", content);
        
        params.put("config", configParams); // 谷歌API_KEY
        params.put("audio", audioParams); // 源语言

        /* 转化为JSON格式 */
        JSONObject jsonObj = JSONObject.fromObject(params);

        /* 请求语音谷歌接口，获取请求结果（JSON），并进行非空检查 */
        String speechResult = WeproUtil.jsonPost(SPEECH_URL + API_KEY, jsonObj);
        if (checkParamNull(speechResult)) {
            resultMap = setResCode(Consts.EXCEPTION_CODE, "谷歌语音识别返回结果为空",
                    Consts.ErrorMsg.MSG00001);
            setResponseEncrypt(resultMap, response);
            
            /* 删除文件 */
            new File(filePath).delete();
            new File(flacFilePath).delete();
            return;
        }
        
        System.out.println("####################speechResult:" + speechResult);
        String speechStr = "";
        
        /* 获取翻译的结果文字 */
        try {
            speechStr = JSONObject.fromObject(speechResult).getJSONArray("results")
                    .getJSONObject(0).getJSONArray("alternatives").getJSONObject(0)
                    .getString("transcript");
        } catch (Exception e) {
            logger.info("获取翻译结果文字失败:"+e.getMessage());
            e.printStackTrace();
        }

        translatParams.put("key", API_KEY); 
        translatParams.put("target", target);
        translatParams.put("q", speechStr);
        
        /* 请求翻译谷歌接口，获取请求结果（JSON） */
        String translatResult = WeproUtil.net(TRANSLATION_URL, translatParams, "GET");
        System.out.println("####################请求参数:" + translatParams);
        System.out.println("####################谷歌翻译结果:" + translatResult);
        if (checkParamNull(translatResult)) {
            resultMap = setResCode(Consts.EXCEPTION_CODE, "谷歌翻译返回结果为空",
                    Consts.ErrorMsg.MSG00001);
            setResponseEncrypt(resultMap, response);
            
            /* 删除文件 */
            new File(filePath).delete();
            new File(flacFilePath).delete();
            return;
        }
        
        /* 特殊符号处理 */
        translatResult = translatResult.replaceAll("&#39;", "'");
        
        System.out.println("**********谷歌翻译结果:" + translatResult);
        
        JSONObject obj = JSONObject.fromObject(translatResult);
        JSONArray resultArray = obj.getJSONObject("data").getJSONArray(
                "translations");

        /* 删除文件 */
        new File(filePath).delete();
        new File(flacFilePath).delete();
        
        /* 若支持语音合成，则返回语音文件，否则语音文件为null */
        String voiceFilePath = getVoice(target, resultArray.getJSONObject(0).getString("translatedText"), request, response);
        
        /* 设置返回结果 */
        resultMap.put("rescode", Consts.SUCCESS_CODE);
        resultMap.put("resmsg_cn", "谷歌翻译请求成功");
        resultMap.put("speechStr", speechStr);
        resultMap.put("translatStr", resultArray);
        // 判断合成语音是否为空，如果为空则返回空字符串
        resultMap.put("voice_file", voiceFilePath);
        System.out.println("**********返回结果:" + resultMap);
        setResponseEncrypt(resultMap, response);
    }
    
    /**
     * 调用百度接口合成语音文件
     * @param request
     * @param response
     * @throws Exception
     */
    public String getBaiduTTS(String lan, String tex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* 设置请求和结果集合 */
        Map<String, String> params = new HashMap<String, String>();
        
        long day = 0;
        if (date != null) {
            Date nowDate = new Date();
            day = (nowDate.getTime() - date.getTime()) / (24 * 60 * 60 * 1000); 
        }
        if (date == null || day > 20) {
            /* 获取百度accessToken */
            /* 设置请求和结果集合 */
            Map<String, String> tokenParams = new HashMap<String, String>();
            Map<String, Object> tokenResultMap = new HashMap<String, Object>();
            
            /* 设置请求参数 */
            tokenParams.put("grant_type", GRANT_TYPE);
            tokenParams.put("client_id", CLIENT_ID);
            tokenParams.put("client_secret", CLIENT_SECRET);
            
            /* 请求百度接口，获取请求结果（JSON） */
            String tokenResult = WeproUtil.net(TOKEN_URL, tokenParams, "GET");
            
            /* 非空检查 */
            if (checkParamNull(tokenResult)) {
                tokenResultMap = setResCode(Consts.EXCEPTION_CODE, "获取accessToken失败",
                        Consts.ErrorMsg.MSG00001);
                setResponseEncrypt(tokenResultMap, response);
                return null;
            }
            
            /* 获取ACCESS_TOKEn */
            ACCESS_TOKEN = JSONObject.fromObject(tokenResult).getString("access_token");
            date = new Date();
        }
        
        /* 设置请求语音合成的参数 */
        params.put("tex", tex);
        params.put("lan", "zh");
        params.put("tok", ACCESS_TOKEN);
        params.put("ctp", "1");
        params.put("cuid", AddressUtil.getMACAddr() == "" ? "cheyr"
                : AddressUtil.getMACAddr());

        /* 设置时间戳 */
        String time = System.currentTimeMillis() + "";
        
        /* 设置请求文件转存地址并添加时间戳
         * 转存上传的文件以添加时间戳的格式保存到指定目录下 */
        // mp3文件本地绝对路径
        String filePath =PathUtils.getWebRootUrl(request) + "/tts/" 
                + time + "tts.mp3";
        // mp3文件服务器地址
        String voicePath =PathUtils.getBaseUrl(request) + "/tts/" 
                + time + "tts.mp3";
        
        /* 请求百度语音合成接口生成语音文件 */
        try {
            WeproUtil.createVoice(VOICE_URL, filePath, params, "GET");
        } catch (Exception e) {
            e.getMessage();
            return "";
        }
        
        return voicePath;
    }
    
    /**
     * 调用MaryTTS接口合成语音文件
     * @param request
     * @param response
     * @throws Exception
     */
    public String getMaryTTS(String lan, String tex, HttpServletRequest request, HttpServletResponse response) throws Exception {
        /* 设置请求和结果集合 */
        Map<String, String> params = new HashMap<String, String>();
        
        /* 设置请求语音合成的参数 */
        params.put("INPUT_TEXT", tex);
        params.put("INPUT_TYPE", "TEXT");
        params.put("OUTPUT_TYPE", "AUDIO");
        params.put("LOCALE", lan);
        params.put("AUDIO", "WAVE_FILE");

        String time = System.currentTimeMillis() + "";
        
        /* 设置请求文件转存地址并添加时间戳
         * 转存上传的文件以添加时间戳的格式保存到指定目录下 */
        // 生成的wav文件本地绝对路径
        String filePath = PathUtils.getWebRootUrl(request) + "/tts/" + time
                + "tts.wav";
        // 需要转换成的mp3文件
        File filePathMp3 = new File(PathUtils.getWebRootUrl(request) + "/tts/"
                + time + "tts.mp3");
        // 转换成功的mp3文件服务器地址
        String voicePath = PathUtils.getBaseUrl(request) + "/tts/" + time
                + "tts.mp3";
        
        System.out.println("filePath####################" + filePath);
        System.out.println("filePathMp3####################" + filePathMp3);
        System.out.println("voicePath####################" + filePathMp3);
        
        /* 请求MaryTTS获取语音文件 */
        try {
            WeproUtil.createVoice(MARYTTS_URL, filePath, params, "GET");
            filePathMp3.createNewFile();
            
            /* 将wav格式转为mp3格式 */
            WavToMp3.execute(new File(filePath), filePathMp3);
            new File(filePath).delete();
        }
        catch (Exception e) {
            e.getMessage();
            return "";
        }
        return voicePath;
    }
    
    /**
     * 浮层开关接口
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/floatSwitch", method = RequestMethod.GET)
    public void floatSwitch(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /* 设置结果集合 */
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String result = "";

        /* 设置请求参数 */
        Map<String, Object> parmMap = new HashMap<String, Object>();
        parmMap.put("xcxId", request.getParameter("xcxId"));

        /* 获取浮层请求结果 */
        try {
            result = switchService.getSwitchStatus(parmMap);
        } catch (Exception e) {
            logger.info("浮层开关解密失败:" + e.getMessage());
            e.printStackTrace();
        }

        /* 非空检查 */
        if (checkParamNull(result)) {
            resultMap = setResCode("700001", "获取失败！", Consts.ErrorMsg.MSG00001);
            setResponseEncrypt(resultMap, response);
            return;
        }

        /* 发送请求结果 */
        setResponseEncrypt(result, response);
    }
    
    /**
     * 获取文字转的声音文件路径
     * 根据文字的语言判断调用的接口
     * 中文：百度
     * 其他支持的语言：MaryTTS
     * @param lan
     * @param tex
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    public String getVoice(String lan, String tex, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        /* 设置异常放回结果 */
        Map<String, Object> resultMap = new HashMap<String, Object>();
        // 生成的文件名
        String filePath = "";
        
        /* 非空检查 */
        if (checkParamNull(lan) || checkParamNull(lan)) {
            resultMap = setResCode("700001", "获取音频参数为空！", Consts.ErrorMsg.MSG00001);
            setResponseEncrypt(resultMap, response);
            return null;
        }
        
        /* 若请求的语言是英语，则设置为美式英语（en_US），英式英语为（en_GB） */
        if ("en".equals(lan)){
            lan = "en_US";
        }
        
        /* 支持语言的数组
         * lb   卢森堡语 
         * te   泰卢固语
         * it   意大利语
         * de   德语 
         * en_US    美式英语
         * en_GB    英式英语
         * fr   法语
         * tr   土耳其语
         * ru   俄语
         * sv   瑞典语
         */
        String[] lanArr = { "lb", "te", "it", "de", "en_US", "fr", "tr" };//, "ru""sv"俄语接口出现问题，等修复后添加
        
        /* 根据识别的目标语言调用不同的接口 */
        if ("zh".equals(lan) || "zh-CN".equals(lan)) {
            /* 中文调用百度接口 */
            filePath = getBaiduTTS(lan, tex, request, response);
        } else {
            for (int i = 0; i < lanArr.length; i++) {
                if (lanArr[i].equals(lan)) {
                    /* 其他支持的语言调用MaryTTS接口 */
                    filePath = getMaryTTS(lan, tex, request, response);
                }
            }
        }
        
        /* 返回合成的语音文件的服务器地址 */
        return filePath;
    }
    @RequestMapping(value = "/getLanguage", method = RequestMethod.GET)
    public void getLanguage(HttpServletRequest request,
            HttpServletResponse response) {
        Map<String, Object> resultMap = new HashMap<String, Object>();
        String path = request.getSession().getServletContext().getRealPath("/language/language.json");
        String languageStr = WeproUtil.readJson(path);
        
        resultMap.put("rescode", Consts.SUCCESS_CODE);
        resultMap.put("resmsg_cn", "语言列表请求成功");
        resultMap.put("language", JSONArray.fromObject(languageStr));
        /* 发送请求结果 */
        setResponseEncrypt(resultMap, response);
    }
}
