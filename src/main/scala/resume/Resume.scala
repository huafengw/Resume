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
      bulletList(bullets:_*),
      paddingBottom := 5,
      paddingTop := 5
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
              div(textAlign.right, greyText, "18621585140"),
              div(textAlign.right, greyText, "unicor_n@163.com"),
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
              row(h2(sectionHeading, "Intel"), logo("Intel.png"), div(rightGreyText, "Shanghai")),
              titledBlock(
                "Software Engineer, Smart Storage Management", "April 2017 - Present",
                """
                SSM is a storage management system built on top of Apache HDFS. It collects file related metrics and
                automatically optimizes HDFS storage policies based on these metrics data along with user's pre-defined rules.
                As a core contributor, I participated in multiple rounds of project architecture design, implemented
                the data-collecting, data-maintenance, distributed execution service and other core features, built
                the dashboard prototype, integrated Travis and Codecov to the project.
                """,
                autolink("github.com/Intel-bigdata/SSM")
              ),
              titledBlock(
                "Software Engineer, MPICH on Yarn", "Feb 2017 - Mar 2017",
                """
                MPICH is a high performance and widely portable implementation of the Message Passing Interface (MPI) standard.
                Some deep learning frameworks, such as Intel-Caffe, use MPICH as their underlying communication framework.
                To bring these frameworks into Hadoop world and leverage Yarn's resource management, I built a prototype that
                can run MPICH applications on Yarn by myself. Now the two examples of MPICH, CPI and Parent & Child, can
                run on Yarn successfully.
                """,
                autolink("github.com/huafengw/mpich-on-yarn")
              ),
              titledBlock(
                "Software Engineer, HiBench", "Jun 2016 - Sep 2016",
                """
                HiBench is a big data benchmark suite that helps evaluate different big data frameworks' performance.
                To cover the vigorous streaming processing frameworks, we developed a streaming module in HiBench and
                use it to evaluated 4 frameworks, including Apache Spark Streaming, Apache Flink, Apache Storm and
                Apache Gearpump. Finally we composed a detailed functional comparison and performance evaluation report
                to summarize our work.
                """,
                autolink("github.com/intel-hadoop/HiBench"),
                autolink("www.slideshare.net/HuafengWang/functional-comparison-and-performance-evaluation-of-streaming-frameworks")
              ),
              titledBlock(
                "Software Engineer, Apache Gearpump", "July 2014 - Present",
                """
                Gearpump is an Intel open sourced lightweight real-time big data streaming engine built on top of Akka.
                I contributed high availability, dynamic DAG, message loss detection and recovery, CGroup support and other
                core features to Gearpump. I also performed several rounds of performance tuning of Gearpump, optimized
                critical hotspot functions and stabilized the JVM’s GC behavior which brought about 35% performance improvement
                against the initial result. Currently, Gearpump is an incubating project under Apache Software Foundation.
                """,
                autolink("github.com/apache/incubator-gearpump")
              ),
              titledBlock(
                "Software Engineer, Open Source", "July 2014 - Present",
                """
                Work in open source community. Contributed patches to Apache Hadoop, Apache Beam, Apache Flink.
                """
              ),
              titledBlock(
                "Software Engineer Intern, Intel Hadoop Distribution", "July 2013 - April 2014",
                """
                NativeTask is a high-performance C++ API & runtime for Hadoop MapReduce, I contributed its support for
                Apache Mahout and Hadoop 2.0 version, and evaluated its performance improvement using HiBench.
                The final result shows 30% boost up compared with the original MapReduce implementation. NativeTask
                will be released in Hadoop 3.0.
                """
              )
            )
        ),
        section(
          "Skills",
          h3(roleText,
            bulletList(
              Seq(
                "Java",
                "Scala",
                "C/C++",
                "Shell",
                "Javascript",
                "SQL"
              ).mkString(" - "),
              Seq(
                "Apache Hadoop",
                "Akka",
                "Apache Kafka",
                "Apache Storm"
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
                div(rightGreyText, "Nanjing Jiangsu")
              ),
              titledBlock(
                "Undergraduate Software Engineering, GPA 4.35/5", "Sep 2010 - Jun 2014"
              )
            )
          )
        ),
        section(
          "Reference",
          col(
            div(
              row(h2(sectionHeading, "Speech"), logo("GoogleSlides.png")),
              titledBlock(
                "Apache Gearpump: Next-Gen Streaming Engine", "Apache Big Data Europe 2016",
                autolink("http://sched.co/8U02")
              ),
              titledBlock(
                "Streaming Report: Functional Comparison and Performance Evaluation", "Apache Big Data Europe 2016",
                autolink("http://sched.co/8U05")
              )
            )
          )
        )
      )
    )
    write.over(cwd/'target/"resume.html", blob.render)
  }
}
