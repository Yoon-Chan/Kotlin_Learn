import io.kotest.core.spec.style.*
import io.kotest.matchers.should
import io.kotest.matchers.shouldBe


class NumberTest : StringSpec({
    "2 + 2 should be 4" { (2 + 2) shouldBe 4 }
    "2 * 2 should be 4" { (2 * 2) shouldBe 4 }
})

//wordSpec 클래스 이용 예제
//should() 호출을 When() 또는 `when`()으로 감싸면 테스트 계층을 3단계로 구성할 수 있다.
class NumberTest2 : WordSpec({
    "1+2" should {
        "be equal to 3" { (1 + 2) shouldBe 3 }
        "be equal to 2 + 1" { (1 + 2) shouldBe (2 + 1) }
    }

    "Addition" When {
        "1+2" should {
            "be equal to 3" { (1 + 2) shouldBe 3 }
            "be equal to 2 + 1" { (1 + 2) shouldBe (2 + 1) }
        }
    }
})


//FunSpec 클래스 예제
// test와 context 블록을 어떤 깊이에서도 사용할 수 있다.
// 단, test 불록을 test 블록 안에 쓸수는 없다.

class NumberTest3 : FunSpec({
    test("0 should be equal to 0") { 0 shouldBe 0 }
    context("Arithmetic") {
        context("Addition") {
            test("2 + 2 should be 4") { (2 + 2) shouldBe 4 }
        }
        context("Multiplication") {
            test("2*2 should be 4") { (2 * 2) shouldBe 4 }
        }
    }
})

//DescribeSpec 클래스 예제

class NumbersTest4 : DescribeSpec({
    describe("Addition") {
        context("1+2") {
            it("should give 3") { (1 + 2) shouldBe 3 }
        }
    }
})

//ShoulSpec 클래스 예제
class NumbersTest5 : ShouldSpec({
    should("be equal to 0") { 0 shouldBe 0 }
    context("Addition") {
        context("1+2") {
            should("be equal to 3") { (1 + 2) shouldBe 3 }
            should("be equal to 2 + 1") { (1 + 2) shouldBe (2 + 1) }
        }
    }
})

//FreeSpec 클래스 예제
class NumbersTest6 : FreeSpec({
    "0 should be equal to 0" { 0 shouldBe 0 }
    "Addition" - {
        "1 + 2" - {
            "1 + 2 should be equal to 3" { (1 + 2) shouldBe 3 }
            "1 + 2 should be equal to 2 + 1" { (1 + 2) shouldBe (2 + 1) }
        }
    }
})

//FeatureSpec 클래스 예제
class NumbersTest7 : FeatureSpec({
    feature("Arithmetic") {
        val x = 1
        scenario("x is 1 at first") { x shouldBe 1 }
        feature("increasing by") {
            scenario("1 gives 2") { (x + 1) shouldBe 2 }
            scenario("2 gives 3") { (x + 2) shouldBe 3 }
        }
    }
})

//BehaviorSpec 클래스 예제
class NumbersTest8 : BehaviorSpec({
    Given("Arithmetic") {
        When("x is 1") {
            val x = 1
            And("increased by 1") {
                Then("result is 2") { (x + 1) shouldBe 2 }
            }
        }
    }
})

//AnnotationcSpec 클래스 예제
//class NumbersTest9 : AnnotationSpec({
//    @Test
//    fun `2 + 2 should be 4`() {
//        (2 + 2) shouldBe 4
//    }
//
//    @Test
//    fun `2*2 should be 4`() {
//        (2 * 2) shouldBe 4
//    }
//})