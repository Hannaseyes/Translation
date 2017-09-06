package com.we.util;

import it.sauronsoftware.jave.AudioAttributes;
import it.sauronsoftware.jave.Encoder;
import it.sauronsoftware.jave.EncodingAttributes;

import java.io.File;

public class WavToMp3 {

    /**
     * 执行转化过程
     * 
     * @param source
     *            源文件
     * @param desFileName
     *            目标文件名
     * @return 转化后的文件
     */
    public static File execute(File source, File target)
            throws Exception {
        AudioAttributes audio = new AudioAttributes();
        audio.setCodec("libmp3lame");
        audio.setBitRate(new Integer(128000)); // 音频比率 MP3默认是1280000
        audio.setChannels(new Integer(2));
        audio.setSamplingRate(new Integer(44100));
        EncodingAttributes attrs = new EncodingAttributes();
        attrs.setFormat("mp3");
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        encoder.encode(source, target, attrs);
        return target;
    }
}