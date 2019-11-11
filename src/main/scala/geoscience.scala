package geoscience

import java.io.File

import jline.console.ConsoleReader
import jline.console.history.FileHistory
import org.clulab.odin.{Mention, ExtractorEngine} // Attachment
import org.clulab.processors.{Document, Processor}
import org.clulab.processors.fastnlp.FastNLPProcessor
import utils._

import scala.collection.immutable.{HashMap, ListMap}
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

// case class AbsTime(year: Int) extends Attachment

object GeoscienceExample extends App {

	// create the processor
  	val fast: Processor = new FastNLPProcessor(useMalt = false, withDiscourse = false)

	// read rules from general-rules.yml file in resources
	val source = io.Source.fromURL(getClass.getResource("/grammars/geo_entities.yml"))
	val rules = source.mkString
	source.close()

	// Create Engine
	val extractor = ExtractorEngine(rules)

	val bufferedSource = io.Source.fromURL(getClass.getResource("/text/SampleText.txt"))
	for (line <- bufferedSource.getLines) {
    	// example sentence
		val text = line.stripMargin

		// annotate the sentence
		var proc = fast
	  	
	  	val doc = proc.annotate(text)

	  	// extract mentions from annotated Document
	  	val mentions = extractor.extractFrom(doc)


	  	// val pubDate = getPubDate(doc)
	  	// val normMentions = attachDate(mentions, pubDate)

	  	// display the mentions
	  	// mentions.foreach{ m => println(m.json(pretty = true))}
	  	displayMentions(mentions, doc)
	}
	bufferedSource.close

	// def attachDate(mentions: Seq[Mention], date: Date): Seq[Mention] = {
	// 	for (m <- mentions) yield {
	// 		if (m matches "TimeExp") {
	// 			val newTime = m.text match {
	// 				case "million" => 1000000 - date
	// 			}
	// 			m.copy(attachments=Set(AbsTime(newTime)))
	// 		} else {
	// 			m
	// 		}
	// 	}
	// }
}



