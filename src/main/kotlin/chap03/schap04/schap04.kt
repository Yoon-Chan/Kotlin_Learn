package chap03.schap04


fun main() {
    c5()
}

fun c5(){
    //꼬리 재귀 함수
    /*
    *  꼬리 재귀 함수에 대한 최적화 컴파일을 지원한다.
    *  코틀린 함수에 tailrec을 붙이면 컴파일러가 재귀 함수를 비재귀적인 코드로 자동으로 변환해 준다.
    *  한 예로 이진 검색을 수행하는 함수를 작성해보자.
    *
    * */
    val array = IntArray(5){ (it+1) * 2}
    println(binIndexOf(5, array, 0))    //-1
    println(binIndexOf(6, array, 0))    //2

}

tailrec fun binIndexOf(
    x : Int,
    array : IntArray,
    from : Int = 0,
    to : Int = array.size
) : Int {
    if(from == to) return -1
    val midIndex = (from + to -1) / 2
    val mid = array[midIndex]
    return when{
        mid < x -> binIndexOf(x, array, midIndex + 1, to)
        mid > x -> binIndexOf(x, array, from, midIndex)
        else -> midIndex
    }
}