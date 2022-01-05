

[![MVVM](https://badgen.net/badge/Alvin/mvvm/green?icon=github)](https://github.com/Chen-Xi-g/MVVMFramework)  [![MVVM](https://jitpack.io/v/Chen-Xi-g/MVVMFramework.svg)](https://jitpack.io/#Chen-Xi-g/MVVMFramework)

# 基于MVVM进行快速开发， 上手即用。

> 对基础框架进行模块分离, 分为 `MVVM Library`--`MVVM Network Library`
> 可基于业务需求使用 `MVVM Library` 或 `MVVM Network Library`

## How to

To get a Git project into your build:

**Step 1**. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:

```
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```

**Step 2**. Add the dependency

```
dependencies {
	implementation 'com.github.Chen-Xi-g:MVVMFramework:v1.0.1'
}
```

