package chap03.schap01


fun main() {
    c4()
}

fun c4(){
    //vararg
    /*
    * 인자가 정해지지 않을 때 사용한다.
    * 아래와 같이 인자가 안정해진 수를 이용할 때 사용
    * 해당 인자는 Array와 같은 성질을 갖는다. 아래의 경우에는 IntArray타입.
    *
    *  vararg은 둘 이상을 파라미터로 선언하는 것은 금지된다.
    *
    *  Spread Operator(*)는 배열을 단순 나열할 때 사용한다.
    *  한 예시로
    *  val array = arrayOf("aa","bb","cc")
    *  val array2 = arrayOf( *array, "dd", "cc")
    *  여기서 array2는 arrayOf("aa","bb", "cc", "dd", "cc")와 같다.
    *
    * */
    printSorted(6,2,10,1) // [1, 2, 6, 10]


    //아래와 같이 사용한 경우 2차원 배열로 생각하며 얕은 복사가 이루어진다.
    val a = intArrayOf(1,2,3)
    val b = intArrayOf(4,5,6)
    change(a,b)
    println(a.contentToString()) // [100, 2, 3]
    println(b.contentToString()) // [4, 5, 6]

}

fun printSorted(vararg items: Int){
    items.sort()
    println(items.contentToString())
}

fun change(vararg items : IntArray){
    items[0][0] = 100
}