---
title: "입/퇴장 알림 플러그인"

---

# 입문 Q&A

### 미니게임 설정 위치 지정은 어떻게 하나요?
미니게임에 참여하여 지정할 위치로 이동한 다음,`/mg debug location` 명령을 입력하여 `` 좌표를 확인한다.  
그리고 미니게임/location/<schematicName>.yml 에 다음과 같이 작성한다.  (schematicName 은 일반적으로 default 이다. )
```yaml
위치이름:
  X: ...
  Y: ...
  Z: ...
  #TAG: static # 위치를 게임 영역의 좌표가 아닌 실제 월드 좌표로 한다.   
```
