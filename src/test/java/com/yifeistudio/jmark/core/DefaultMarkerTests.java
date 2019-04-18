package com.yifeistudio.jmark.core;

import com.yifeistudio.jmark.core.impl.DefaultMarkerImpl;
import org.junit.Test;

public class DefaultMarkerTests {


    @Test
    public void loadFileTest() {
        DefaultMarker defaultMarker = new DefaultMarkerImpl("/home/yi/Downloads/Packt.Learning.Aurelia.2016.12.pdf");
    }
}
