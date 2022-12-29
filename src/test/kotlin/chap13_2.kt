import io.kotest.assertions.assertSoftly
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.assertions.timing.continually
import io.kotest.assertions.timing.eventually
import io.kotest.core.spec.style.StringSpec
import io.kotest.inspectors.*
import io.kotest.matchers.*
import io.kotest.matchers.ints.*
import io.kotest.matchers.string.shouldEndWith
import sun.nio.ch.IOStatus
import java.io.File
import kotlin.time.Duration
import kotlin.time.Duration.Companion.seconds
import kotlin.time.ExperimentalTime

//주어진 수가 홀수인지 검사하는 매처를 만들기
fun beOdd() = object : Matcher<Int> {
    override fun test(value: Int): MatcherResult {
        return MatcherResult(
            value % 2 != 0,
            { "$value should not be odd" },
            {
                "$value should be odd"
            }
        )
    }
}

class NumbersTestWithOddMatcher : StringSpec({
    "5 is positive odd" { 5 should (beOdd() and  positive())}
    "4 is not odd" {4 should beOdd()}
    //"7 is positive odd" {7 should BeLessThan(10)} 은
    //아래와 같은 방식이다.
    "7 is positive odd" {7 shouldBeLessThan(10)}
})

//compose()는 Deprecated가 되어 compose 대슨 contramap을 이용하여 사용하도록 권장한다.
//이 함수는 주어진 컬렉션의 길이가 홀수인지 검사한다.
fun beOddLength() = beOdd().contramap<Collection<*>> { it.size  }
//fun beOddLength() = beOdd().compose<> {  }<Collection<*>> { it.size  }



//인스펙터 사용 예제

class NumbersTestWithInspectors : StringSpec({
    val numbers = Array(10) {it + 1}

    "all are non-negative" {
        numbers.forAll { it shouldBeGreaterThanOrEqual 0 }
    }

    "none is zero" {numbers.forNone { it shouldBe 0 } }

    "a single 10" {numbers.forOne { it shouldBe 10 }}

    "at most one 0" {numbers.forAtMostOne { it shouldBe 0 }}

    "at least one odd number" { numbers.forAtLeastOne { it % 2 shouldBe 1 }}

    "at most five odd numbers" {
        numbers.forAtMost(5) {it % 2 shouldBe 1}
    }

    "at least three even numbers"{
        numbers.forAtLeast(3) { it % 2 shouldBe 0}
    }

    "some numbers are odd" {numbers.forAny { it % 2 shouldBe 1 }}

    "some but not all number are even" {
        numbers.forSome { it % 2 shouldBe 0 }
    }

    "exactly five numbers are even" {
        numbers.forExactly(5){ it % 2 shouldBe 0}
    }


})

//예외처리 예제
class ParseTest : StringSpec({
    "invaild string" {
        val e = shouldThrow<NumberFormatException> { "abc".toInt()  }
        e.message shouldEndWith "\"abc\""
    }
})

//assertSoftly 예외처리 예제
//assertSoftly가 없다면 첫 번재 forall에서 실패하기때문에 두 번째가 실행되지 않는다.
//하지만 코드에서 assertSoftly가 있으므로 두 단언문이 실행되고 테스트가 예외와 함께 종료된다.
class NumbersTestWithForAll : StringSpec({
    val numbers = Array(10){it+1}
    "invalid numbers"{
        assertSoftly {
            numbers.forAll { it shouldBeLessThan 5}
            numbers.forAll { it shouldBeGreaterThan 3 }
        }
    }
})

//eventually 예제
class StringSpecWithEventually : StringSpec({
    "10초 안에 파일의 내용이 단 한 줄인 경우가 있어야 함" {
        eventually(/*Duration.seconds(10)은 Deprecated가 되어 이와 같이 작성*/10.seconds){
            //주어진 기간 동안 파일이 한 줄만 들어있는 순간이 올 때까지 검사(최대 10초)
            File("data.txt").readLines().size shouldBe 1
        }
    }
})

//continually() 함수 예제
class StringSpecWithContinually : StringSpec({
    "10초 동안 파일의 내용이 계속 한 줄로 유지되어야 함" {
        continually(10.seconds){
            File("data.txt").readLines().size shouldBe 1
        }
    }
})

//속성 기반 테스트 예제
//infix fun Int.min(n: Int) = if(this < n) this else n
//
//class NumberTestWithAssertAll  : StringSpec({
//    "min" {
//        checkAll { a : Int, b: Int ->
//            (a min b).let {
//                it should (beLessThanOrEqualTo(a) and beLessThanOrEqualTo(b))
//            }
//        }
//    }
//
//})

