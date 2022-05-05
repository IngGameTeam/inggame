---
title: "inggame-alert"

[//]: # (# To set og:image:)
[//]: # (# image: ...)
---

# inggame-alert 이 무엇입니까?

다양한 값이 저장되어 있는 yml 파일을 파일 트리 구조로 관리하는 역할을 하는 모듈입니다.  

## 저장되는 타입 
- string
- int
- double
- string list
- inventory
- item stack(readable serialization, internal serializing, support both)
- location
- alert
  - chat
  - title
  - action bar
  - click/hover message


## 트리 구조

```
└─inggame-alert-test-plugin
    ├─simple_minigame  
    │   ├─alert  
    │   │      en.yml  
    │   │      ko.yml  
    │   │  
    │   ├─location  
    │   │      en.yml  
    │   │      ko.yml  
    │   │    
    │   └─string  
    │           en.yml  
    │           ko.yml  
    └─test_minigame
        ├─alert  
        │      en.yml  
        │      ko.yml  
        │  
        └─string  
                en.yml  
                ko.yml  
```
  
```java  

//use case  
import io.github.inggameteam.alert.AlertPluginImpl  
  
class PartyPlugin : AlertPluginImpl() {  
  
    override fun onEnable() {  
        super.onEnable()  
        println(component.string.comp("enable_plugin"))  
    }  
}```  
