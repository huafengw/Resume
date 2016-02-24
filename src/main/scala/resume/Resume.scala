package resume

import ammonite.ops._
import Styles._
import Styles.sheet._
import scalatags.Text.all._
object Resume{
  def dataUri(filepath: Path) = {
    "data:image/png;base64," +
    javax.xml.bind.DatatypeConverter.printBase64Binary(
      read.bytes! filepath
    )
  }

  def main(args: Array[String]) = {
    def autolink(url: String) = a(url.stripPrefix("https://").stripPrefix("http://"), href:=url)
    def row = div(display.flex, flexDirection := "row")
    def col = div(display.flex, flexDirection := "column")
    def titledBlock(title: String, loc: String, bullets: Frag*) = div(
      row(
        h3(roleText, title),
        div(rightGreyText, loc)
      ),
      bulletList(bullets:_*)
    )
    def bulletList(bullets: Frag*) = ul(
      listBlock,
      bullets.map(li(listItem, _))
    )
    def quickBullet(lhs: String, rhs: String) = tr(
      td(div(para, roleText, paddingRight := 5, lhs)),
      td(para, rhs)
    )
    def logo(s: String) = {
      img(height := 15, src := dataUri(cwd/'images/s))
    }
    def section(title: String, body: Frag) = tr(
      td(h2(paddingTop := 10, paddingBottom := 10, sectionHeading, title, marginRight := 20)),
      td(paddingTop := 10, paddingBottom := 10, body)
    )
    def talk(name: String, loc: String, video: String) = div(
      row(h3(roleText, name), div(rightGreyText, loc)),
      ul(listBlock, li(listItem, autolink(video)))
    )

    val blob = html(
      fontFamily := "Calibri, Candara, Segoe, 'Segoe UI', Optima, Arial, sans-serif",
      head(
        scalatags.Text.tags2.style(raw(cssReset)),
        scalatags.Text.tags2.style(raw(sheet.styleSheetText))
      ),
      body(
        width := 720,
        boxSizing.`border-box`,
        padding := 30,
        row(
          width := "100%",
            div(
              width := "50%",
              h1(nameText, "Wang Huafeng")
            ),
            col(
              width := "50%",
              div(textAlign.right, greyText, "fvincent@gmail.com"),
              div(
                textAlign.right,
                greyText,
                autolink("http://www.github.com/huafengw")
              )
            )
          )
        ),
        hr,
        table(
          width := "100%",
          section(
            "Work",
            col(
              row(h2(sectionHeading, "Intel"), logo("Intel.png"), div(rightGreyText, "Shanghai, China")),
              titledBlock(
                "Software Engineer, Gearpump", "Sep 2014 - Present",
                """
                Core committer to Gearpump, a lightweight real-time big data streaming engine over Akka.
                """
              ),
              titledBlock(
                "Software Engineer Intern, Intel Hadoop Distribution", "July 2013 - Mar 2014",
                """
                Contributed security features to IDH, which is based on MIT Kerberos
                """,
                """
                Contributed to mapreduce-nativetask, which is a native optimization for MapTask and
                boosted mapreduce performance up to 30%.
                """
              )
            )
        ),
        section(
          "Buzzwords",
          h3(roleText,
            bulletList(
              Seq(
                "Scala",
                "Javascript",
                "Java",
                "Shell",
                "SQL"
              ).mkString(" - "),
              Seq(
                "Akka",
                "Storm",
                "Kafka",
                "Hadoop"
              ).mkString(" - ")
            )
          )
        ),
        section(
          "Education",
          col(
            div(
              row(
                h2(sectionHeading, "Nanjing University"),
                // Override height to compensate for non-square image
                logo("NJU.png")(height := 12, paddingTop := 4),
                div(rightGreyText, "Jiangsu, China")
              ),
              titledBlock(
                "Undergraduate Software Engineering", "Sep 2010 - Jun 2014"
              )
            )
          )
        ),
        section(
          "Reference",
          col(
            div(
              row(h2(sectionHeading, "Projects"), logo("Github.png")),
//              div(listBlock,
//                p(para,
//                  "Other cool projects i've worked on that are worth checking out!"
//                )
//              ),
              titledBlock(
                "Gearpump", "Sep 2014 - Present",
                """
                Gearpump is a lightweight real-time big data streaming engine over Akka
                """,
                autolink("https://github.com/gearpump/gearpump")
              ),
              titledBlock(
                "NativeTask",
                """
                A native optimization for MapTask in Hadoop MapReduce framework based on JNI.
                """,
                autolink("https://issues.apache.org/jira/browse/MAPREDUCE-2841")
              )
            )
          )
        )
      )
    )
    write.over(cwd/'target/"resume.html", blob.render)
  }
}
