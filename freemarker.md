    FreeMarker(프리마커) 문법


http://freemarker.org/docs/index.html

FreeMarker 는 Velocity와 마찬가지로 templating 언어이다.
우리가 Jsp를 코딩하다보면 날코딩으로 생산성이 떨어진다. 그래서 우리는 좀더 편리하게 사용하기위해서 템플릿 엔진을 사용한다.
그리고 가장 큰 장점은 아래의 1번에서 보듯이 매크로 기능으로 기능을 만들어서 사용할 수 있다는 점이다.
다음은 FreeMarker의 사용 방법을 기술했다.
FTL tag
<# >


주석
<#--주석달기-->


반복문
1.
<#list [Object code에서 key값 ]  as  [별칭할 값]>
 
2. for(int i=0;i<10;i++)
<#list  1..10  as i >
      ${i}
   <#assign i=i+1?int>
 
3. 사이즈를 알고 싶을때.. Key 값이 list 인 경우
 
<#assign size=list?size>
 
4. 다른 변수로 정의하고 싶을때에는
<#setting [새로]=[기존]>

 
5. 변수선언
<#assign x=0>   <#--x에 0를 할당해 준것이다. -->
x값을 출력하고자 할때 --> ${x}


6. <#macro green>
“<@green>”이런식으로 쓴다.
주로 변하지 않는 변수를 이렇게 선언해서 쓴다.
 
7. 조건문
<#if>
 
8. int형으로 선언해 주고 싶은때에는
<#assign x=0 ? int>
 

1. @macro
- 프리마커 템플릿 전역에서 공통으로 사용되는 UI 디펜던트한 함수는 매크로로 만들어 여러 ftl에서 사용할 수 있도록 해준다. 샘플을 참고하도록 한다.
- 형식 : <@매크로명 변수1, 변수2, ... />
- 샘플1) 긴 문자열을 적당한 크기로 자르는 기능의 매크로
*사용법 : <@trimX ${item.title}, 20 />
*매크로 :
<#macro trimX src max><#compress>
	<#if src?length &gt; max>
	${src[0..max-1]}..
	<#else>
	${src}
	</#if>
</#compress></#macro>

 

- 샘플2) YYYYMMDD 형식의 문자열을 YYYY.MM.DD 형식으로 변환하는 매크로
*사용법 : <@parseDay ${item.regdate} />
*매크로 :
<#macro parseDay src><#compress>
	<#if src?length == 8>
	${src[0..3]}.${src[4..5]?number}.${src[6..7]?number}
	<#else>
	${src}
	</#if>
</#compress></#macro>

 

2. #list
- 배열 형식의 오브젝트를 루핑 처리할때 사용하는 프리마커 지시자이다. “로컬엘리어스_index” 라는 변수는 0부터 시작하는 시퀀스번호이다.
- 형식 : <#list 배열객체 as 로컬엘리어스명></#list>
- 샘플1)
<#list LIST as item>
번호 : ${item_index+1} | 이름 : ${item.name} | 아이디 : ${item.id}
</#list>

 

3. #if
- 프리마커 조건문에 사용되는 지시자이다.
- 형식 : <#if 조건식></#if>
- 샘플1) string 비교
<#if ENTITY.usergrade == “A” >......</#if>
- 샘플2) number 비교
<#if ENTITY.userclass?number == 3>.....</#if>
- 샘플3) boolean 비교
<#if ENTITY.isAuth()>.....</#if>

 

4. #break
- Loop문을 중단하고 다음 스크립트로 넘어가려고 할때 사용되는 지시자이다.
- 형식 : <#break>
- 샘플1) 루프문을 실행하는 중 5번째에서 escape 하는 예
<#list LIST as item>
<#if item_index &gt; 3><#break></#if>
</#list>

 

5. #assign
- 프리마커내에서 사용자 정의 로컬변수가 필요할 때 사용하는 지시자이다.
- 형식 : <#assign 로컬변수명 = 초기화값>
- 샘플1) <#assign CHECK = item_index>

 

6. [x...y]
- 문자열의 일정 범위를 자를때 사용하는 함수
- 형식 : ${문자열[1..5]}
- 샘플1) ${item.name[1..5]}

 

7. ?has_content
- 리스트형 오브젝트가 null이 아니고 최소 1개 이상의 컨텐츠를 가지고 있는지 체크하는 함수로써 ?has_content는 ?exists와 ?size>0 두가지 체크를 동시에 해주는 함수이다.
- 형식 : 리스트오브젝트?has_content
- 샘플1) <#if LIST?has_content>.....</#if>

 

8. ?exists
- NULL체크 함수. if_exists는 <#if 지시자 없이도 사용할 수 있게 해주는 표현식이다.
- 형식 : 오브젝트?exists
- 샘플1) <#if ENTITY.username?exists>${ENTITY.username?substring(0, 5)}</#if>
- 샘플2) <#if LIST?exists && LIST?size &gt; 0>.....</#if>
- 샘플3) ${ENTITY.username?if_exists}

 

9. ?default
- NULL값을 대체해주는 함수
- 형식 : 오브젝트?default(디폴트값)
- 샘플1) ${item.userclass?default(“99”)}
- 샘플2) ${item.age?default(20)}

 

10. ?string
- 문자열로 형변환하는 함수
- 형식 : 오브젝트?string
- 샘플1) <#if item.age?string == “29”>.....</#if>
- 샘플2) ${item.regdate?string(“yyyy/MM/dd HH:mm”)}
- 샘플3) 숫자를 통화표시로 나타내는 예
<#assign MONEY = 1234567>
${MONEY?string(",##0")}

 

11. ?number
- 숫자로 형변환하는 함수
- 형식 : 오브젝트?number
- 샘플1) <#if item.userclass?number &gt; 3>.....</#if>
- 샘플2) ${LIST_POINTS[item.gid?number].entityname?default(“”)}

 

12. ?js_string
- 문자열을 자바스크립트에 유효하도록 필터링해주는 함수. 문자열내에 싱글쿼테이션(‘)등이 포함되어 스크립트에 오류가 나는것을 방지하기 위하여 사용되는 함수이다. 화면상에는 HTML 태그로 취급된다.
- 형식 : 오브젝트?js_string
- 샘플1) 문자열 <img src=’/image/enterprise.gif’>을 js_string으로 처리했을때 소스보기를 하면 <img src=\’/image/enterprise.gif\’>으로 출력된다.
- 샘플2) <a href=”javascript:getName(‘${item.homeurl?js_string}’);”>

 

13. ?html
- 문자열을 HTML Symbolic Entity로 필터링해주는 함수. 문자열내의 HTML태그등을 깨뜨려 화면상으로 출력되도록 할때 사용하는 함수이다. 화면상에 HTML태그가 아닌 일반 문자열로 취급된다.
- 형식 : 오브젝트?html
- 샘플1) 문자열 <img src=’/image/enterprise.gif’>을 html로 처리하면 화면상에 <img src=’/image/enterprise.gif’> 로 출력되고 소스보기를 하면 &lt;img src=’/image/enterprise.gif’&gt;로 출력된다.


14. ?index_of
- 특정 문자(열)가 시작되는 위치를 정수형으로 반환한다. 인덱스는 0부터 시작됨.
- 형식 : 오브젝트?index_of(특정문자)
- 샘플1) “abcde”?index_of(“c”) 는 2를 반환한다.


15. ?replace
- 문자열의 일부를 주어진 문자로 대체하는 함수
- 형식 : 오브젝트?replace(찾을문자열, 대체할문자열)
- 샘플1) ${item.content?replace(“>”, “&gt;”)}

 

16. item_has_next
-리스트 객체의 다음 컨텐츠가 존재하는지(EOF) 체크하는 함수
-형식 : 리스트엘리어스이름_has_next
-샘플1) 이름과 이름사이에 , 를 찍어주되 마지막은 찍지 않는 경우의 예
<#list LIST as item>
${item.name?default(“”)}<#if item_has_next>,</#if>
</#list>


