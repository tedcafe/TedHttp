# TedHttp ![](https://img.shields.io/github/license/mashape/apistatus.svg)


Simple (and humble ^^;;) Android HttpURLConnection wrapping library.
(I just started Android and Java development few weeks ago. ^^;;)



## Features
* Easy to use.
* **Asynchrous** request and handle response in callback code block.
  (with AsyncTask)





## Request

### POST
```java
    TedHttp.post("http://someurl.com/some.do", new TedHttpSession.CompleteCallback() {
        @Override
        public void onComplete(TedHttpRequest req, TedHttpResponse res) {
            // do something for result
        }
    ).start();
```

#### parameter
You can add parameter with parameter(String, Object) dot method chainning.

```java
    TedHttp.post("http://someurl.com/some.do", new TedHttpSession.CompleteCallback() {
        @Override
        public void onComplete(TedHttpRequest req, TedHttpResponse res) {
            // do something for result
        }
    )
    .parameter("name1", "value1")
    .parameter("name2", "value2")
    .start();
```

or with HashMap parameter.

```java
    HasnMap<String, Object> params = new HashMap<>();

    params.put("name1", "value1");
    params.put("name2", "value2");

    TedHttp.post("http://someurl.com/some.do", params, new TedHttpSession.CompleteCallback() {
        @Override
        public void onComplete(TedHttpRequest req, TedHttpResponse res) {
            // do something for result
        }
    )
    .start();
```


#### header


```java
    TedHttp.post(...)
    .header("X-Header", "BlaBla")
    .start();
```


### GET

GET is same as POST except that parameters placed in querystring of URI.

```java
    TedHttp.get("http://someurl.com/some.do", new TedHttpSession.CompleteCallback() {
        @Override
        public void onComplete(TedHttpRequest req, TedHttpResponse res) {
            // do something for result
        }
    ).start();
```








## Response

#### response body
```java
    ...
    @Override
    public void onComplete(TedHttpRequest req, TedHttpResponse res) {
        if ( res.isOk() ) {
            String responseBody = res.responseBody;
            // do some.
        } else {
            // error
        }
    }
    ...
```


#### JSON
```java
    ...
    @Override
    public void onComplete(TedHttpRequest req, TedHttpResponse res) {
        if ( res.isOk() ) {        
            JSONObject json = res.json();
            // do some.
        } else {
            // error
        }
    }
    ...
```







## Settings

You can set some settings.

#### For globally
(once enough)

```java
    TedHttp.defaultConfig.connectionTimeout = 10000;
    TedHttp.defaultConfig.readTimeout = 10000;
```

####Or for a request.

```java
    TedHttp.post(...)
    .readTimeout(5000)
    .connectionTimeout(5000)
    .start();
```






## Roadmap

* Handle file upload and progress callback.
