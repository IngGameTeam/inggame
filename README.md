**Currently, in development.**

**Inggame is a minecraft plugin development framework based on Kotlin**

### **Use as a framework**

Inggame is able to run as a plugin supported framework

## Design Goals
- **동적 리셉션**: 모든 엔티티가 거대한 데이터 구조의 래퍼(wrapper)이기 때문에, 엔티티의 프로퍼티에 리스코프 치환 원칙을 적용함.
- **단일 모델**: 모든 데이터 객체 모델을 런타임에서 커스텀 POJO Json 형식 직렬화를 통해 로드하고 저장할 수 있음.

