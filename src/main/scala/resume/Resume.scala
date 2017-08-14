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
              h1(nameText, "王华峰")
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
            "工作经历",
            col(
              row(h2(sectionHeading, "英特尔"), logo("Intel.png"), div(rightGreyText, "上海")),
              titledBlock(
                "软件工程师, Smart Storage Management", "2017年4月至今",
                """
                SSM是一个基于HDFS的智能存储管理系统，它能够收集与文件相关的各种Metrics，并根据这些数据以及用户定义的一些规则，
                自动优化整个文件系统的存储行为。我作为该项目的核心开发人员，完成了多次架构设计的迭代，实现了项目中的数据收集与管理，
                分布式执行服务等核心功能，搭建了前端界面原型并集成了Travis和Codecov项目管理工具，使得该项目在构建初期能够稳定
                且快速地开展工作。
                """,
                autolink("github.com/Intel-bigdata/SSM")
              ),
              titledBlock(
                "软件工程师, MPICH on Yarn", "2017年2月-2017年3月",
                """
                很多相对陈旧的分布式处理应用使用的是MPI(Message-Passing Interface)实现，包括某些深度学习框架，比如Intel-Caffe，
                且MPICH是MPI标准的一种重要实现。基于上述背景，我独立开发了一个旨在能够让原生MPICH应用直接运行在Yarn上的项目，
                希望使得原本脱离于大数据集群的应用资源也能被Yarn纳入进来。现该项目已能成功运行MPICH的CPI和Parent and Child两个示例。
                """,
                autolink("github.com/huafengw/mpich-on-yarn")
              ),
              titledBlock(
                "软件工程师, HiBench", "2016年6月-2016年9月",
                """
                HiBench是Intel开源的一个大数据框架性能测试系统，为了能够覆盖近年来火热的流处理框架，需要为其添加流处理系统的测试模块，
                我参与设计了该模块的架构以及测试用例，之后对Apache Spark Streaming, Apache Flink, Apache Storm,
                Apache Gearpump四个框架进行了全面的功能和性能测评，最终完成了一份翔实的测评报告。
                """,
                autolink("github.com/intel-hadoop/HiBench"),
                autolink("www.slideshare.net/HuafengWang/functional-comparison-and-performance-evaluation-of-streaming-frameworks")
              ),
              titledBlock(
                "软件工程师, Apache Gearpump", "2014年7月至今",
                """
                Gearpump是Intel开源的一个基于Akka开发的实时流处理平台，我开发了其中的高可用，应用逻辑的动态修改，消息丢失容错，
                CGroup支持等功能，并数次为该框架进行性能调优，使得框架在基准测试中取得了35%的吞吐量提升以及更低的延迟。
                Gearpump现已成为Apache基金会孵化器项目。
                """,
                autolink("github.com/apache/incubator-gearpump")
              ),
              titledBlock(
                "软件工程师, 开源社区", "2014年7月至今",
                """
                参与大数据开源社区的开发工作，为Apache Hadoop, Apache Beam, Apache Flink贡献patch。
                """
              ),
              titledBlock(
                "软件工程师实习生, 英特尔Hadoop发行版", "2013年7月-2014年4月",
                """
                NativeTask是Hadoop MapReduce的高效Native执行引擎实现，我实现了NativeTask对Apache Mahout的支持
                以及Hadoop 2.0版本的升级，并使用HiBench对其进行性能测试，结果显示对比原生MapReduce有30%的性能提升。
                该模块将于Hadoop 3.0版发布。
                """
              )
            )
        ),
        section(
          "技能",
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
          "教育经历",
          col(
            div(
              row(
                h2(sectionHeading, "南京大学"),
                // Override height to compensate for non-square image
                logo("NJU.png")(height := 12, paddingTop := 4),
                div(rightGreyText, "江苏")
              ),
              titledBlock(
                "软件工程本科, GPA 4.35/5", "2010年9月-2014年6月"
              )
            )
          )
        ),
        section(
          "参考",
          col(
            div(
              row(h2(sectionHeading, "演讲"), logo("GoogleSlides.png")),
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
