package me.izzp.jetsurveydemo

import androidx.annotation.DrawableRes

enum class QuestionType {
    single,
    multi,
    date,
    progress,
    photo,
}

data class ProgressValues(
    val steps: Int,
    val titles: Array<String>,
)

data class Option(
    val title: String? = null,
    @DrawableRes val image: Int = 0,
) {
    companion object {
        fun strings(vararg values: String) = values.map {
            Option(it)
        }.toTypedArray()


        fun images(vararg values: Int) = values.map {
            Option(null, it)
        }.toTypedArray()
    }
}

data class Question(
    val question: String,
    val type: QuestionType,
    val options: Array<Option>? = null,
    val progressValues: ProgressValues? = null,
)

val questionsData: List<Question> = listOf(
    Question(
        "In my free time I like to ...",
        QuestionType.multi,
        arrayOf(
            Option("吃"),
            Option("游戏"),
            Option("乒乓球"),
            Option("画画"),
            Option("唱歌")
        )
    ),
    Question(
        "喜欢的角色",
        QuestionType.single,
        arrayOf(
            Option("bug_of_chaos", R.drawable.bug_of_chaos),
            Option("frag", R.drawable.frag),
            Option("lenz", R.drawable.lenz),
            Option("spark", R.drawable.spark),
        )
    ),
    Question(
        "最喜欢的电影",
        QuestionType.single,
        Option.strings("英雄", "泰冏", "烈日灼心", "追凶者也")
    ),
    Question(
        "讨厌的角色",
        QuestionType.multi,
        Option.images(R.drawable.bug_of_chaos, R.drawable.frag, R.drawable.lenz, R.drawable.spark)
    ),
    Question(
        "最值得纪念的日子",
        QuestionType.date,
    ),
    Question(
        "你有多喜欢钱？",
        QuestionType.progress,
        progressValues = ProgressValues(
            steps = 4,
            titles = arrayOf("不喜欢", "还行", "超喜欢")
        )
    ),
    Question(
        "自拍一个吧",
        QuestionType.photo,
    )
)