# Java/Spring 테스트를 추가하고 싶은 개발자들의 오답노트

도구를 어떻게 사용할지 사용법에 대해 이야기 하지 않는다. 근본적인 문제, '테스트의 가치'에 대해 이야기한다.

테스트는 도입하는 이유는 회귀버그(Regression Test)[^1] 방지다. 하지만 의도와 다르게 동작하는 테스트는 비결정적인 테스트[^2]로 변질되어 자신감을 떨어트린다.
짐이 된 테스트, 결국 우리는 테스트와 멀어진다. 그렇다면 지식 공유자가 강조하는 부분은 무엇일까? 테스트를 쉽게 하기 위해서는 '유연한 설계'가 중요하다고 강조한다. 어떻게 회귀버그를 방지할까?

TDD(Test Driven Development)는 갑론을박이 있는 주제다. TDD의 장점으로 많이 이야기하는 내용은 '객체지향'이다. (구현체보다 행동에 집중한다).

테스트를 작성하면서 테스트가 보내는 신호를 무시하지 말자. 무리헌 업무로 지칠 때 몸에서 신호를 보내듯 테스트는 신호를 보낸다. 이러한 신호는 '유연한 설계'를 위한 신호다.
하지만 아무리 좋은 방법이어도 실무에 도입하는 건 쉽지 않다. 어떻게 제어할 수 없는 것에 의존하지 않을 수 있을까?

### 테스트에 필요한 개념

테스트를 이야기할 때 많이 이야기하는 키워드만 선별해서 다룬다. SUT(System under test), BDD, 상태검증과 행위검증, 테스트 더블 등 다양한 키워드가 있다. 특히 지식공유자가 주목한 키워드는
Testability와 테스트 대역(Dummy, Fake, Stub)이다.

**테스트 대역**

- Dummy: 아무런 동작은 없지만 정상적인 실행을 위해 사용되는 객체를 의미한다.

```typescript
export class DummyNotificationService extends NotificationService {
  sendMessage(user: User): void {
    // 아무 동작도 정의하지 않는다.
  }
}
```

- Fake: 자체 로직이 구현을 가진다. 로컬에서 사용하거나 테스트에 사용하기 위해 만들어진 가짜 객체를 의미한다.

```typescript
export class FakeInMemoryUserRepository implements UserRepository {
  private users: Record<string, User> = {};

  save(user: User): void {
    this.users[user.username] = user;
  }

  findByUsername(username: string): User | undefined {
    return this.users[username];
  }
}
```

- Stub: 미리 준비된 값을 출력하는 객체를 의미한다. 외부 의존성을 가질 때 대신 사용한다. 주로 mockito 프레임워크와 함께 사용된다.

```typescript
export class StubUserRepository implements UserRepository {
  getByEmail(email: string) {
    if (email === "foo@bar.com") {
      return User.of("foo@bar.com", "PENDING")
    }

    throw new UsernameNotFoundException(email);
  }
}
```

### 의존성과 Testability (1)

"테스트를 이야기하다가 갑자기 의존성?"

**의존성(Dependency)**

'다른 객체를 사용할 때 의존성을 가진다'라고 이야기한다. 예를 들어 일할 때 사수에게 도움을 받으면 나는 '사수에게 의존성을 가진다'라고 할 수 있다. 그렇다면 의존성은 잘못된 것일까?
정답은 아니다. 객체는 서로 협력한다. 따라서 필수다. 하지만 과한 의존은 유지보수를 어렵게 한다. 의존성을 약하게 할 수는 없을까?

**의존성 주입(Dependency Injection)**

의존성 주입(Dependency Injection)은 의존성을 약하게 하는 여러 방법 중 하나다. 객체를 new 키워드로 직접 만들지 않고 만들어진 객체를 주입한다. 의존성 주입과 자주 헷갈리는 개념은 의존성
역전이다. 의존성 역전은 무엇일까?

**의존성 역전(Dependency Inversion Principle)**

의존성 역전은 화살표의 방향을 바꾼다. 변화가 많은 구현체에 직접 의존하지 않고 변화가 적은 추상화에 의존한다. 클라이언트도 추상화에 의존하고, 구현체도 추상화에 의존한다.

### 의존성과 Testability (2)

"테스트를 잘하고 싶어? 연락해~"

앞서 의존성을 이야기한 이유는 테스트를 잘하려면 자연스럽게 의존성에 손이 간다. 의존성이 숨겨진 코드는 테스트를 어렵게 한다. '어렵다', '프레임워크 도움이 필요한데?'와 같은 증상이 보인다면
테스트 코드가 보내는 구조신호다. 그렇다면 어떻게 문제를 해결할 수 있을까?

이때 의존성 주입과, 의존성 역전을 사용한다. 우선 숨겨진 의존성을 주입하도록 변경한다. 이어서 추상화에 의존하도록 변경하면 이전보다 검증이 쉽다.

**테스트 가능성(Testability)**

"얼마나 쉽게 입력을 변경하며 출력을 검증할 수 있는가?"

테스트 가능성(Testability)은 쉽게 테스트할 수 있는지 평가하는 지표다. 결국 우리는 테스트 가능성이 높은 코드를 작성해야 한다.

### 실기 전 사전 탐색

- 빌더
- 엔티티
- private / final
- DRY < DAMP
- 논리로직

**빌더(Builder)**

빌더 패턴(Builder Pattern)은 생성자가 많아지는 문제를 해결하기 위해 사용한다. new 키워드로 객체를 선언했다면 어떨까? 생성자를 수정하면 관련된 객체를
모두 수정해야 한다. 반면 빌더는 필요한 매개변수(Parameter)만 입력할 수 있게 돕는다. 물론 단점도 있다. 매개변수를 누락해도 컴파일러가 에러를 찾기 어렵다. 따라서 주의가 필요하다.

**엔티티(Entity)**

엔티티(Entity)는 크게 3가지[^3] 관점으로 다르게 정의한다. 나누기 모호한 주제지만 "지식공유자는 나누는게 좋지않을까?"라는 의견을 밝힌다.

1. 도메인

도메인에서 엔티티는 비즈니스 문제를 해결하기 위해 객체다. (JPA와 관련없다)

```typescript
export class User {
  id: number;
  name: string;
}
```

2. 데이터베이스

데이터베이스에서 엔티티는 관계형데이터베이스(Relational database, RDB)에 저장되는 객체다.

```sql
CREATE TABLE Users
(
    ID   INT PRIMARY KEY,
    NAME VARCHAR(50),
);
```

3. 영속성

영속성에서 엔티티는 도메인과 데이터베이스를 연결하기 위해 사용하는 객체다. TypeORM과 같은 ORM(Object-relational mapping) 기술을 사용해 문제를 해결한다.

```typescript
@Entity()
export class User {
  @PrimaryGeneratedColumn()
  id: number;

  @Column()
  name: string;
}
```

**기타 조언**

- private / final 메서드: 테스트하지 않는다. 만약 어려움을 느낀다면 잘못설계되어 있다는 신호를 보낸다.
- DRY < DAMP: DRY(Don't Repeat Yourself)는 강조되는 원칙이다. 하지만 테스트는 DAMP(Descriptive And Meaningful Phrase)를 권장한다.
  '테스트만 읽더라도 어떤 내용인지 파악할 수 있도록 기술하자'는 의미다
- 논리 로직을 피하라: 테스트 코드에 if, for, 덧셈 연산자 같은 논리 로직을 피하고 직관적으로 작성하자는 뜻이다.

## 느낀점

강의가 구조적이다. 살아있는 책을 읽는 것 같다. 개념을 여러 장으로 나눠서 설명하는 게 인상적이다. 인용을 적극적으로 활용하고 출처를 표시하여 추가 학습 기회를 제공한다.
만약, 이미 접한 도서가 있다면 지식공유자의 사례를 듣고 쉽게 적용할 수 있을 것 같다.

**<참고 자료>**

- [김우근 'Java/Spring 테스트를 추가하고 싶은 개발자의 오답노트'](https://inf.run/EYKf)
- [블라디미르 코리코프 '『단위 테스트』'](https://product.kyobobook.co.kr/detail/S000001805070)
- [인프런 질문 & 답변](https://www.inflearn.com/questions/146128)
- [X 'DRY < DAMP'](https://x.com/dylayed/status/1506405375897051136)

[^1]: 회귀버그란 이전에 작동했던 기능이 개선 후 동작을 멈추는 현상이다.
[^2]: 비결정적인 테스트란 동일한 입력과 조건에서도 동일한 결과를 보장하지 않는다. 데이터베이스, 시스템에 의존할 때 발생한다.
[^3]: 도메인, 영속성 객체, 데이터베이스
