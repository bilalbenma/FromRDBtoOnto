/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.reasoner;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Jan-2010
 */
public class TimedConsoleProgressMonitor implements ReasonerProgressMonitor {

    private int lastPercentage = 0;
    private long lastTime;
    private final ThreadMXBean bean=ManagementFactory.getThreadMXBean();
    private long beginTime;

    @Override
    public void reasonerTaskStarted(String taskName) {
        System.out.print(taskName);
        System.out.println(" ...");
        lastTime=bean.getCurrentThreadCpuTime();
        beginTime=lastTime;
    }

    @Override
    public void reasonerTaskStopped() {
        System.out.println("    ... finished in "+((bean.getCurrentThreadCpuTime()-beginTime))/1000000D);
        lastPercentage = 0;

    }

    @Override
    public void reasonerTaskProgressChanged(int value, int max) {
    	long time=bean.getCurrentThreadCpuTime();
        if (max > 0) {
            int percent = (value * 100) / max;
            if (lastPercentage != percent) {
                System.out.println("    "+percent+"%\t"+((time-lastTime)/1000000));
                lastTime=time;
                lastPercentage = percent;
            }
        }
    }

    @Override
    public void reasonerTaskBusy() {
        System.out.println("    busy ...");
    }
}
