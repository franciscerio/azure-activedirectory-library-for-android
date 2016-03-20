// Copyright (c) Microsoft Corporation.
// All rights reserved.
//
// This code is licensed under the MIT License.
//
// Permission is hereby granted, free of charge, to any person obtaining a copy
// of this software and associated documentation files(the "Software"), to deal
// in the Software without restriction, including without limitation the rights
// to use, copy, modify, merge, publish, distribute, sublicense, and / or sell
// copies of the Software, and to permit persons to whom the Software is
// furnished to do so, subject to the following conditions :
//
// The above copyright notice and this permission notice shall be included in
// all copies or substantial portions of the Software.
//
// THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
// IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
// FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
// AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
// LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
// OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
// THE SOFTWARE.

package com.microsoft.aad.adal;

/**
 * Settings to be used in AuthenticationContext.
 */
public enum AuthenticationSettings {
    /**
     * Singleton setting instance.
     */
    INSTANCE;

    private static final int SECRET_RAW_KEY_LENGTH = 32;

    private byte[] mSecretKeyData = null;

    private String mBrokerPackageName = AuthenticationConstants.Broker.COMPANY_PORTAL_APP_PACKAGE_NAME;

    private String mBrokerSignature = AuthenticationConstants.Broker.COMPANY_PORTAL_APP_SIGNATURE;

    private Class<?> mClazzDeviceCertProxy;

    private String mActivityPackageName;

    /**
     * SharedPreference package name to load this file from different context.
     */
    private String mSharedPrefPackageName;

    /**
     * set to be false in default 
     * if user want to use broker
     * the mUseBroker should be set explicitly by calling {@link #setUseBroker()} 
     */
    private boolean mUseBroker = false;

    /**
     * Expiration buffer in seconds.
     */
    private int mExpirationBuffer = 300;

    private int mConnectTimeOut = 30000;

    private int mReadTimeOut = 30000;

    /**
     * Get bytes to derive secretKey to use in encrypt/decrypt.
     * 
     * @return byte[] secret data
     */
    public byte[] getSecretKeyData() {
        return mSecretKeyData;
    }

    /**
     * set raw bytes to derive secretKey to use in encrypt/decrypt. KeySpec
     * algorithm is AES.
     * 
     * @param rawKey App related key to use in encrypt/decrypt
     */
    public void setSecretKey(byte[] rawKey) {
        if (rawKey == null || rawKey.length != SECRET_RAW_KEY_LENGTH) {
            throw new IllegalArgumentException("rawKey");
        }

        mSecretKeyData = rawKey;
    }

    /**
     * Gets packagename for broker app that installed authenticator.
     * 
     * @return packagename
     */
    public String getBrokerPackageName() {
        return mBrokerPackageName;
    }

    /**
     * Sets package name for broker app that installed authenticator.
     * 
     * @param packageName package name related to broker
     */
    public void setBrokerPackageName(String packageName) {
        mBrokerPackageName = packageName;
    }

    /**
     * Gets broker signature for broker app that installed authenticator.
     * 
     * @return signature
     */
    public String getBrokerSignature() {
        return mBrokerSignature;
    }

    /**
     * Sets broker app info for ADAL to use.
     * 
     * @param brokerSignature Signature for broker
     */
    public void setBrokerSignature(String brokerSignature) {
        this.mBrokerSignature = brokerSignature;
    }

    /**
     * set class for work place join related API. This is only used from
     * Authenticator side.
     * 
     * @param <T>
     * @param clazz
     */
    public void setDeviceCertificateProxyClass(Class clazz) {
        if (IDeviceCertificate.class.isAssignableFrom(clazz)) {
            mClazzDeviceCertProxy = clazz;
        } else {
            throw new IllegalArgumentException("clazz");
        }
    }

    /**
     * get class for work place join related API. This is only used from
     * Authenticator side.
     * 
     * @return
     */
    public Class<?> getDeviceCertificateProxy() {
        return mClazzDeviceCertProxy;
    }

    /**
     * get package name to setup intent for AuthenticationActivity.
     * 
     * @return Package name for activity
     */
    public String getActivityPackageName() {
        return mActivityPackageName;
    }

    /**
     * set package name to setup intent for AuthenticationActivity.
     * 
     * @param activityPackageName activity to use from different package
     */
    public void setActivityPackageName(String activityPackageName) {
        this.mActivityPackageName = activityPackageName;
    }

    /**
     * @deprecated As of release 1.1.14, replaced by {@link #getUseBroker()}
     */
    @Deprecated public boolean getSkipBroker() {
        return !mUseBroker;
    }

    /**
     * @deprecated As of release 1.1.14, replaced by {@link #setUseBroker()}
     */
    @Deprecated public void setSkipBroker(boolean skip) {
        mUseBroker = !skip;
    }

    /**
     * Get broker usage.
     * 
     * @return true if broker is used.
     */
    public boolean getUseBroker() {
        return mUseBroker;
    }

    /**
     * Set flag to use or skip broker.
     * By default, the flag value is false and ADAL will not talk to broker.
     * 
     * @param useBroker True to use broker
     */
    public void setUseBroker(boolean useBroker) {
        this.mUseBroker = useBroker;
    }
    
    /**
     * Sets package name to use {@link DefaultTokenCacheStore} with sharedUserId
     * apps.
     * 
     * @param packageNameForSharedFile Package name of other app
     */
    public void setSharedPrefPackageName(String packageNameForSharedFile) {
        this.mSharedPrefPackageName = packageNameForSharedFile;
    }

    /**
     * Gets package name provided for shared preferences.
     * 
     * @return package name provided for shared preferences
     */
    public String getSharedPrefPackageName() {
        return mSharedPrefPackageName;
    }

    /**
     * Gets expiration buffer.
     * 
     * @return
     */
    public int getExpirationBuffer() {
        return mExpirationBuffer;
    }

    /**
     * When checking access token expiration, it will check if the time to
     * expiration is less than this value(in seconds). Example: Set to 300 to
     * give 5min buffer. Token with Expiry time of 12:04 will say expired when
     * actual time is 12:00 with 5min buffer.
     * 
     * @param expirationBuffer
     */
    public void setExpirationBuffer(int expirationBuffer) {
        this.mExpirationBuffer = expirationBuffer;
    }

    public int getConnectTimeOut() {
        return mConnectTimeOut;
    }

    /**
     * Sets the maximum time in milliseconds to wait while connecting.
     * Connecting to a server will fail with a SocketTimeoutException if the
     * timeout elapses before a connection is established. Default value is
     * 30000 milliseconds.
     * 
     * @param timeOutMillis the connect timeout in milliseconds. Non-negative
     * @throws IllegalArgumentException if timeoutMillis < 0.
     */
    public void setConnectTimeOut(int timeOutMillis) {
        if (timeOutMillis < 0) {
            throw new IllegalArgumentException("Invalid timeOutMillis");
        }
        this.mConnectTimeOut = timeOutMillis;
    }

    public int getReadTimeOut() {
        return mReadTimeOut;
    }

    /**
     * Sets the maximum time to wait for an input stream read to complete before
     * giving up. Reading will fail with a SocketTimeoutException if the timeout
     * elapses before data becomes available. The default value is 30000.
     * 
     * @param timeoutMillis the read timeout in milliseconds. Non-negative
     */
    public void setReadTimeOut(int timeOutMillis) {
        if (timeOutMillis < 0) {
            throw new IllegalArgumentException("Invalid timeOutMillis");
        }

        this.mReadTimeOut = timeOutMillis;
    }
}
