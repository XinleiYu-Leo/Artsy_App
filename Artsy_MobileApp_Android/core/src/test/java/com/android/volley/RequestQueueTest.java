/*
 * Copyright (C) 2011 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.android.volley;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

import com.android.volley.mock.ShadowSystemClock;
import com.android.volley.toolbox.NoCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.utils.ImmediateResponseDelivery;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/** Unit tests for RequestQueue, with all dependencies mocked out */
@RunWith(RobolectricTestRunner.class)
@Config(shadows = {ShadowSystemClock.class})
public class RequestQueueTest {

    private ResponseDelivery mDelivery;
    @Mock private Network mMockNetwork;

    @Before
    public void setUp() throws Exception {
        mDelivery = new ImmediateResponseDelivery();
        initMocks(this);
    }

    @Test
    public void cancelAll_onlyCorrectTag() throws Exception {
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        Object tagA = new Object();
        Object tagB = new Object();
        StringRequest req1 = mock(StringRequest.class);
        when(req1.getTag()).thenReturn(tagA);
        StringRequest req2 = mock(StringRequest.class);
        when(req2.getTag()).thenReturn(tagB);
        StringRequest req3 = mock(StringRequest.class);
        when(req3.getTag()).thenReturn(tagA);
        StringRequest req4 = mock(StringRequest.class);
        when(req4.getTag()).thenReturn(tagA);

        queue.add(req1); // A
        queue.add(req2); // B
        queue.add(req3); // A
        queue.cancelAll(tagA);
        queue.add(req4); // A

        verify(req1).cancel(); // A cancelled
        verify(req3).cancel(); // A cancelled
        verify(req2, never()).cancel(); // B not cancelled
        verify(req4, never()).cancel(); // A added after cancel not cancelled
    }

    @Test
    public void add_notifiesListener() throws Exception {
        RequestQueue.RequestEventListener listener = mock(RequestQueue.RequestEventListener.class);
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        queue.addRequestEventListener(listener);
        StringRequest req = mock(StringRequest.class);

        queue.add(req);

        verify(listener).onRequestEvent(req, RequestQueue.RequestEvent.REQUEST_QUEUED);
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void finish_notifiesListener() throws Exception {
        RequestQueue.RequestEventListener listener = mock(RequestQueue.RequestEventListener.class);
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        queue.addRequestEventListener(listener);
        StringRequest req = mock(StringRequest.class);

        queue.finish(req);

        verify(listener).onRequestEvent(req, RequestQueue.RequestEvent.REQUEST_FINISHED);
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void sendRequestEvent_notifiesListener() throws Exception {
        StringRequest req = mock(StringRequest.class);
        RequestQueue.RequestEventListener listener = mock(RequestQueue.RequestEventListener.class);
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        queue.addRequestEventListener(listener);

        queue.sendRequestEvent(req, RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_STARTED);

        verify(listener)
                .onRequestEvent(req, RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_STARTED);
        verifyNoMoreInteractions(listener);
    }

    @Test
    public void removeRequestEventListener_removesListener() throws Exception {
        StringRequest req = mock(StringRequest.class);
        RequestQueue.RequestEventListener listener = mock(RequestQueue.RequestEventListener.class);
        RequestQueue queue = new RequestQueue(new NoCache(), mMockNetwork, 0, mDelivery);
        queue.addRequestEventListener(listener);
        queue.removeRequestEventListener(listener);

        queue.sendRequestEvent(req, RequestQueue.RequestEvent.REQUEST_NETWORK_DISPATCH_STARTED);

        verifyNoMoreInteractions(listener);
    }
}
