# hello-db
### Spring 환경에서 MyBatis를 통한 DB 사용.
1) pom.xml(gradle 빌드도구를 사용할 경우 build.gradle)에 의존성 추가. <br/>
    + Spring-mybatis, mybatis, DB드라이버, DataSource <br/>
2) Application Context에 bean을 추가<br/>
    + DataSource<br/>
    + ** SqlSessionFactoryBean (DataSource에 의존한다) ** <br/>
        mapperLocations 속성으로 SQL Mapper(xml로 정의함)의 위치를 지정한다.<br/>
    + SqlSessionTemplate (SqlSessionFactory에 의존한다)<br/>
    + (선택) TransactionManager (DataSource에 의존한다)<br/>
3) XML 파일로 SQL Mapper를 정의한다<br/>
    + <mapper> 요소의 namespace 속성과 <select, insert, update...> 등 쿼리 요소의 id를 매핑에 사용한다.<br/>
4) DAO등 클래스에서 사용한다<br/>
    + 스프링 컨테이너에서 SqlSession 클래스로 의존성을 받는다. (@Autowired, 생성자 주입 등)<br/>
    + SqlSession 인스턴스의 메서드를 사용해 SQL Map에 있는 SQL을 호출한다.<br/>
    + 예) sqlSession.selectList("BakedBean.select", [parameter]);<br/>

### 참고자료
https://mybatis.org/mybatis-3/ko/index.html
