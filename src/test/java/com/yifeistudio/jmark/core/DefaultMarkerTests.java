package com.yifeistudio.jmark.core;

import com.yifeistudio.jmark.core.impl.DefaultMarkerImpl;
import org.junit.Test;

public class DefaultMarkerTests {


    @Test
    public void loadFileTest() {
        DefaultMarker defaultMarker = DefaultMarkerImpl.builder()
                .filePath("/home/yi/Downloads/Packt.Learning.Aurelia.2016.12.pdf")
                .outputPath("/home/yi/Desktop/xxx.pdf")
                .build();
        defaultMarker.mark("机密文件");

    }
}
