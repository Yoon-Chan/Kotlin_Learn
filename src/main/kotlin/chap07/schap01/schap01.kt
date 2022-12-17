package chap07.schap01

//아래와 같이 Comparable인터페이스를 상속받고
//오버라이드를 하여 비교를 진행한다.
class Person(
    val firstName : String,
    val familyname : String,
    val age : Int
) : Comparable<Person> {
    val fullName get() = "$firstName $familyname"
    override fun compareTo(other: Person): Int = fullName.compareTo(other.fullName)
}

fun main() {
   //c1()
    c2()
}

fun c1(){
    /*
    *   Compareable과 Comparator
    *
    *   코틀린도 자바처럼 Comparable과 Comparator 타입을 지원하며, 몇몇 컬렉션 연산에 이를 활용한다.
    *
    * */


    //다른 방법으로 Comparator를 이용하는 방법이다
    val AGE_COMPARATOR = Comparator<Person> { p1, p2 ->
        p1.age.compareTo(p2.age)
    }

    //compareBy()나 compareByDescending() 함수를 통해 대상 객체 대신 사용할 수 있는 비교 가능 객체를
    //제공하게 함으로써 비교기를 만들 수 있다.

    val AGE_COMPARATOR2 = compareBy<Person> { it.age }
    val REVERSE_AGE_COMPARATOR = compareByDescending<Person> { it.age }

    /*
    *   컬렉션 생성하기
    *
    *   emptyList() / emptySet() : 불변인 빈 리스트/ 집합 인스턴스를 생성한다.
    *   listOf() / setOf() : 인자로 제공한 배열에 기반한 불변 리스트/집합 인스턴트를 만든다.
    *   listOfNotNull() : 널인 값을 걸러내고 남은 원소들로 이뤄진 새 불변 리스트를 만든다.
    *   mutableListOf()/ mutableSetOf() 가변 리스트/집합의 디폴트 구현 인스턴스를 만든다.
    *   arrayListOf() : 새로운 ArrayList를 만든다.
    *   hashSetOf()/ linkedSetOf()/ sortedSetOf() : HashSet/ LinkedHashSet/ TreeSet의 새 인스턴스를 만든다.
    *
    *
    * */


    /*
    *   원소가 알려진 시퀀스를 만드는 가장 단순한 방법은 sequenceOf() 함수를 사용하는 것이다.
    *   이 함수는 가변 인자를 받는다.
    *
    *   맵에 대해 asSequence()를 호출하면 맵 엔트리(키, 값 쌍을 감싼 타입)의 시퀀스를 얻는다.
    * */

    println(sequenceOf(1,2,3).iterator().next()) //1
    println(listOf(10, 20, 30).asSequence().iterator().next()) //10
    println(mapOf(1 to "One", 2 to "Two").asSequence().iterator().next()) // 1=One

    /*
    *   또한, 제너레이터 함수를 바탕으로 시퀀스를 만드는 방법도 있다.
    * */

    //이 코드는 숫자가 아닌 입력을 받거나 입력이 끝날 때까지 프로그램 입력값을 돌려주는 시퀀스를 만든다.
    val numbers = generateSequence { readlnOrNull()?.toIntOrNull() }

    //다른 방법으로는 아래가 있다.  1, 2, 4, 8, 16, 32, ...
    val powers = generateSequence(1) { it * 2}

    //유한 시퀀스 10, 8, 6, 4, 2, 0
    val evens = generateSequence(10) { if( it >= 2) it - 2 else null}

    //원소를 시퀀스에 추가하는 형식인 yield(), yieldAll()이 있다.
    val numbers2 = sequence {
        yield(0)
        yieldAll(listOf(1,2,3))
        yieldAll(intArrayOf(4,5,6).iterator())
        yieldAll(generateSequence(10){ if ( it < 50) it * 3 else null})
    }

    println(numbers2.toList()) // [0, 1, 2, 3, 4, 5, 6, 10, 30, 90]
}


fun c2(){
    /*
    *   모든 컬렉션이 기본으로 지원하는 공통 연산으로는 이터레이션이 있다.
    *   배열, 이터러블, 시퀀스, 맵은 iterator() 함수를 지원한다.
    *   하지만 코틀린이 같은 일을 할 수 있는 더 간결한 방법을 제공하므로 실무에서 이 이터레이터를 사용하는 경우는 드물다.
    *
    * */

    //이터레이터슬 사용하지 않고 아래와 같이 사용할 수 있다.
    //이 방법은 구조 분해를 쓸수 있는 방식으로 이터레이터 할 수 있는 역할을 한다.
    val map = mapOf(1 to "One", 2 to "Two", 3 to "Three")
    for((key, value) in map){
        println("$key -> $value")
        /*
        * 1 -> One
        2 -> Two
        3 -> Three
        * */
    }

    //위의 for루프의 방식의 대체로 forEach() 가 있다.
    intArrayOf(1,2,3).forEach { println(it * it) } // 1 4 9

    listOf("a", "b", "c").forEach { println("'$it'") } // 'a' 'b' 'c'

    sequenceOf("a", "b", "c").forEach { println("'$it'") } // 'a' 'b' 'c'

    mapOf(1 to "One", 2 to "Two", 3 to "Three").forEach{(key, value) ->
        println("$key -> $value")

        /*
        *   1 -> One
            2 -> Two
            3 -> Three
        *
        * */
    }

    /*
    *  컬렉션 타입의 기본 기능은 다음과 같다.
    *   - size 프로퍼티는 원소 개수를 돌려준다.
    *   - isEmpty() 함수는 컬렉션에 원소가 없는지 검사한다.
    *   - contains()/ containsAll() 함수는 인자로 지정한 원소나 인자로 지정한 컬렉션의 모든 원소가 수신 객체 컬렉션에 들어있는지 검사한다.
    *
    *   contains 함수 호출을 in으로 대체할 수 있긴 하다.
    * */

    val list = listOf(1,2,3)
    println(list.isEmpty()) // false
    println(list.size) // 3
    println(list.contains(4)) //false
    println(2 in list)  //true
    println(list.containsAll(listOf(1,2))) //true
}


fun c3(){
    /*
    *  MutableCollection 타입은 컬렉션을 수정할 수 있는 메서드를 제공한다.
    * */

    /*
    *   1. list
    *
    *
    * */

    // 불변 컬렉션이든 가변 컬렉션이든 +와 - 연산자를 지원한다.
    val list = arrayListOf(1,2,3)
    list.add(4)         // [1,2,3,4]        == list += 4
    list.remove(3)      //[1,2,4]   == list -= 3
    list.addAll(setOf(5,6))     //[1,2,4,5,6]   == list += setOf(5,6)
    list.removeAll(listOf(1,2)) //[4,5,6]       == list -= listOf(1,2)
    list.retainAll(listOf(5,6,7))   //[5,6]
    list.clear()    // 모든 원소 제거 []

    //리스트는 인덱스로 접근이 가능하다. get() 함수를 사용할 수 있지만 인덱스 형식을 선호한다.
    val list2 = listOf(1,4,6,2,4,1,7)
    println(list2.get(3))    //2
    println(list2[2])        //6
    //println(list2[10])       // 인덱스 어레이 오류 == 범위 밖이다.
    println(list2.indexOf(4))    // 1    -> 4의 위치 번호
    println(list2.lastIndexOf(4))    // 4 -> 4의 마지막 위치 번호
    println(list2.indexOf(8))       // -1 -> 해당 번호가 없으면 -1

    /*
    *   2. map
    *   map의 MutableMap 인스터스는 list와 비슷하게 +, - 연산자를 지원한다.
    * */

    val map = mapOf(1 to "I",  2 to "V", 10 to "X", 50 to "L")
    println(map.isEmpty())  // false
    println(map.size)       // 4
    println(map.get(5))   // V
    println(map[10])        // X
    println(map[100])       // null
    println(map.getOrDefault(100, "?")) // ?
    println(map.getOrElse(100) { "?"})  // ?
    println(map.containsKey(10))        // true
    println(map.containsValue("C"))     //false
    println(map.keys)       // [1,5,10,50]
    println(map.values)     // [I, V, X, L]
    println(map.entries)    // [1=I, 5=V, 10=X, 50=L]


}


fun c4(){
    /*
    *
    *   컬렉션에 대한 조건 검사
    *   all() 함수는 컬렉션의 모든 원소가 주어진 술어를 만족하면 true를 반환한다.
    * */

    println(listOf(1,2,3,4).all { it < 10 })    // true
    println(listOf(1,2,3,4).all { it % 2== 0 }) //false
    println(
        mapOf(1 to "I", 5 to "V", 10 to "X")
            .all { it.key == 1 || it.key % 5 == 0 }
    )   //true

    val seq = generateSequence(1) { if( it < 50) it *3 else null }

    println(seq.all { it % 3 == 0 }) // false
    println(seq.all { it == 1 || it % 3 == 0 })     //true

    /*
    *   none() 함수도 있다.
    *   none() 함수는 all()과 반대다. 주어진 조건을 만족하는 원소가 하나도 없을 때 true를 반환한다.
    *
    *   any()함수는 컬렉션 원소 중 적어도 하나가 주어진 술어를 만족할 때 true를 반환한다.
    * */

    /*
    *   컬렉션 원소의 합계를 계산하거나 최댓값을 찾는 것처럼 컬렉션 내용으로부터 한 값을 계산해내는 경우를 집계(aggregation)라고 부른다.
    *   집계의 함수는
    *   count(), sum(), average(), minOrNull(), maxorNull()
    *   minByOrNull(), maxByOrNull(), minWithOrNull(), maxWithOrNull(), joinToString(), joinTo()
    *   fold(), reduce() .. 등등이 있다.
    *
    * */

    /*
    *   켈렉션에서 조건을 만족하지 못하는 우너소를 걸러내 버리고, 원하는 원소만 남기는 여러 확장 함수를 제공한다.
    *   기본적인 걸러내기 연산은 filter() 함수이다.
    *
    *   filterNot() 함수는 조건을 부정해 걸러낼 수 있게 한다.
    * */

    println(listOf("red","green", "blue", "green").filter { it.length > 3 })    // [green,blue, green]
    println(mapOf("I" to 1, "V" to 5, "X" to 10, "L" to 50).filter { it.value > 5 })    //{X=10, L=50}
}

