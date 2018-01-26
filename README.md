<h1 align="center">FYMVP</h1>
<h2 align="center">一个只管自己用，整合了一丢丢主流开源项目，暂时定义是高度可配置化的 Android MVP 快速集成框架</h2>


## Dependencies

* repositories 
```groovy
allprojects {
     repositories {
	  ...
	  maven { url "https://jitpack.io" }
     }
}
```

* Gradle
```groovy
compile 'com.github.edifangyi:FYMVP:-SNAPSHOT'
```

## Config

* 因为框架内依赖了 ButterKnife ，故你的项目在依赖框架后，需要在你的 主Module 的 build.gradle 中添加

```groovy
android {
    ...
    defaultConfig {
	...
	javaCompileOptions {
	    annotationProcessorOptions {
		includeCompileClasspath true
	    }
	}
    }
}
```

## Usage
> 没想好怎么写，自己先看Demo吧
[<img src="http://ss1.sinaimg.cn/large/648b709cly1fntv7f388wj206j031q2s.jpg" width="80" height="80">]


## Functionality & Libraries

1. [`Butterknife`JakeWharton大神出品的view注入框架.](https://github.com/JakeWharton/butterknife)
2. [`OkGo`一个基于 okhttp 的标准 RESTful 风格的网络框架.](https://github.com/jeasonlzy/okhttp-OkGo)
3. [`Rxjava2`提供优雅的响应式Api解决异步请求以及事件处理.](https://github.com/ReactiveX/RxJava)
4. [`Gson`Google官方的Json Convert框架.](https://github.com/google/gson)
5. [`KLog`一个 Android 专用的 LogCat 工具.](https://github.com/ZhaoKaiQiang/KLog)
