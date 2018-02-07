package com.example.android.forzahomeassignmentteams;

import android.test.suitebuilder.TestSuiteBuilder;

import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * Created by Eleftherios Ch. on 7/2/2018.
 * Software Developer
 * lefterisxris@gmail.com
 */

public class FullTestSuite extends TestSuite {

    public static Test suit(){
        return new TestSuiteBuilder(FullTestSuite.class).includeAllPackagesUnderHere().build();
    }

    public FullTestSuite() {
        super();
    }
}
