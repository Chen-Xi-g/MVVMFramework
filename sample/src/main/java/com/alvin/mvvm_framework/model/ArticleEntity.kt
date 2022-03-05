package com.alvin.mvvm_framework.model


import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * 文章数据
 * 接口来源于: [玩Android 开放API](https://www.wanandroid.com/blog/show/2)
 *
 * @property apkLink String?
 * @property audit Int?
 * @property author String? 作者
 * @property canEdit Boolean?
 * @property chapterId Int?
 * @property chapterName String?
 * @property collect Boolean?
 * @property courseId Int?
 * @property desc String?
 * @property descMd String?
 * @property envelopePic String?
 * @property fresh Boolean?
 * @property host String?
 * @property id Int?
 * @property link String?
 * @property niceDate String? 发表时间
 * @property niceShareDate String?
 * @property origin String?
 * @property prefix String?
 * @property projectLink String?
 * @property publishTime Long?
 * @property realSuperChapterId Int?
 * @property selfVisible Int?
 * @property shareDate Long?
 * @property shareUser String?
 * @property superChapterId Int?
 * @property superChapterName String?
 * @property tags List<Tag>?
 * @property title String? 标题
 * @property type Int?
 * @property userId Int?
 * @property visible Int?
 * @property zan Int?
 * @constructor
 */
@Parcelize
data class ArticleEntity(
    val apkLink: String? = "",
    val audit: Int? = 0,
    val author: String? = "",
    val canEdit: Boolean? = false,
    val chapterId: Int? = 0,
    val chapterName: String? = "",
    val collect: Boolean? = false,
    val courseId: Int? = 0,
    val desc: String? = "",
    val descMd: String? = "",
    val envelopePic: String? = "",
    val fresh: Boolean? = false,
    val host: String? = "",
    val id: Int? = 0,
    val link: String? = "",
    val niceDate: String? = "",
    val niceShareDate: String? = "",
    val origin: String? = "",
    val prefix: String? = "",
    val projectLink: String? = "",
    val publishTime: Long? = 0,
    val realSuperChapterId: Int? = 0,
    val selfVisible: Int? = 0,
    val shareDate: Long? = 0,
    val shareUser: String? = "",
    val superChapterId: Int? = 0,
    val superChapterName: String? = "",
    val tags: List<Tag>? = listOf(),
    val title: String? = "",
    val type: Int? = 0,
    val userId: Int? = 0,
    val visible: Int? = 0,
    val zan: Int? = 0
) : Parcelable