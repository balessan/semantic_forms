package deductions.runtime.html

import org.scalatest.FunSuite
import org.w3.banana.FOAFPrefix
import org.w3.banana.Prefix
import org.w3.banana.RDF
import org.w3.banana.RDFOps
import org.w3.banana.diesel._
import deductions.runtime.jena.RDFStoreObject
import deductions.runtime.utils.Fil‍eUtils
import java.nio.file.Paths
import java.nio.file.Files
import org.w3.banana.diesel._
import org.w3.banana._
import org.w3.banana.binder._
import org.w3.banana.syntax._
import org.w3.banana.jena.JenaModule

class TestCreationForm2 extends FunSuite 
//with RDFOpsModule 
with CreationForm with TurtleWriterModule {
//   Prefix[Rdf]("bla" , "bli" )
//   FOAFPrefix[Rdf]
//RDFOps[Rdf]
   println( ops.__xsdString ) // TODO debug !!!!!!!!
   
  test("display form custom") {
    Fil‍eUtils.deleteLocalSPARL()
    val uri = "http://xmlns.com/foaf/0.1/Person"
    retrieveURI( ops.makeUri(uri), dataset )
    rdfStore.appendToGraph( dataset, ops.makeUri("test"), graphTest.personFormSpec )
    val form = create(uri, lang="fr") 
    val file = "creation.form.2.html"
    Files.write(Paths.get(file), form.toString().getBytes );
    println( s"file created $file" )
    assert ( ! form . toString() . contains("homepage") )
    assert (   form . toString() . contains("firstName") )
    assert (   form . toString() . contains("lastName") )
  }

  
//  trait GraphTest /* [Rdf <: RDF] */ extends /*JenaModule with */ RDFOpsModule {
////    implicit val ops: RDFOps[Rdf] = ops.asInstanceOf[org.w3.banana.RDFOps[Rdf]] // TODO : suspect !!!!!!!!!
//    import ops._
//    import syntax._
//    
//    println( ops ) // TODO debug !!!!!!!!
//    val form = Prefix[Rdf]("form", "http://deductions-software.com/ontologies/forms.owl.ttl#")
//    println( ops.makeUri("blabla")) // TODO debug !!!!!!!!
//    val foaf = FOAFPrefix[Rdf]
//    val personFormSpec = (
//      URI("personForm")
//      -- form("classDomain") ->- foaf.Person
//      -- form("showProperties") ->- ( // list
//        bnode("p1") -- rdf.first ->- foaf.firstName
//                    -- rdf.rest ->- (
//          bnode("p2") -- rdf.first ->- foaf.lastName
//                      -- rdf.rest ->- rdf.nil))).graph
//  }
  
  val graphTest = new AnyRef with JenaModule with GraphTest
  println(turtleWriter.asString( graphTest.personFormSpec, "blabla" ))
}

trait GraphTest extends RDFOpsModule {
    import ops._
    import syntax._
    println( ops ) // TODO debug !!!!!!!!
//    println( classOf[Rdf#URI] ) // TODO debug !!!!!!!!
    val form = Prefix[Rdf]("form", "http://deductions-software.com/ontologies/forms.owl.ttl#")
    println( form ) // TODO debug !!!!!!!!
    val foaf = FOAFPrefix[Rdf]
    val personFormSpec = (
      URI("personForm")
      -- form("classDomain") ->- foaf.Person
      -- form("showProperties") ->- ( // list
        bnode("p1") -- rdf.first ->- foaf.firstName
                    -- rdf.rest ->- (
          bnode("p2") -- rdf.first ->- foaf.lastName
                      -- rdf.rest ->- rdf.nil))).graph
  }