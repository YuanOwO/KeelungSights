package xyz.yuanowo.keelungsightsviewer.crawler

import org.jsoup.nodes.Element
import org.jsoup.nodes.TextNode


class CrawlerUtils {

    companion object {

        fun getIDFromURL(url: String): Long? {
            val matchResult = Regex("/tourguide/scenery(\\d+)\\.html").find(url)
            return matchResult?.groups?.get(1)?.value?.toLongOrNull()
        }

        fun appendLF(sb: StringBuilder) {
//         換到新的一行，但如果已在行首則不換行
            if (sb.isNotEmpty() && sb.last() != '\n') {
                sb.append('\n')
            }
        }

        fun getTextFromElement(element: Element?): String? {
            if (element == null)
                return null

            return buildString {
                for (e in element.childNodes()) {
                    when (e) {
                        is TextNode -> {
                            append(e.text().trim())
                        }

                        is Element -> {
                            val text = e.text().trim()
                            if (e.className() == "author" || e.className() == "clear" || e.id() == "po") {
                                continue
                            } else if (e.className() == "othermsg" || e.className() == "othermsg2") {
                                appendLF(this)
                                append(getTextFromElement(e))
                            } else {
                                when (e.tagName()) {
                                    "a" -> append(text)
                                    "strong" -> append("**$text**")
                                    "br" -> append("\n")
                                    "h4" -> {
                                        appendLF(this)
                                        append("#### $text\n")
                                    }

                                    "p" -> {
                                        appendLF(this)
                                        append(text)
                                        appendLF(this)
                                    }

                                    else -> println("Unknown element: $e")
                                }
                            }
                        }

                        else -> {
                            println("Unknown node: $e")
                        }
                    }
                }
            }.trim()
        }

    }

}