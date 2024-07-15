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

**<참고 자료>**

- [김우근 'Java/Spring 테스트를 추가하고 싶은 개발자의 오답노트'](https://inf.run/EYKf)
- [블라디미르 코리코프 '『단위 테스트』'](https://product.kyobobook.co.kr/detail/S000001805070)

[^1]: 회귀버그란 이전에 작동했던 기능이 개선 후 동작을 멈추는 현상이다.
[^2]: 비결정적인 테스트란 동일한 입력과 조건에서도 동일한 결과를 보장하지 않는다. 데이터베이스, 시스템에 의존할 때 발생한다.
