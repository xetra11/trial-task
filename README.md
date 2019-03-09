#### Start as webapp
* Run with `web` spring profile
* Call wep api `localhost:8080/start`

#### Start as CLI
*  run jar file

#### Configuration

Edit the `application.properties` as followed:

* `processor.pair.ids` The id the pair of documents have in common ie. `123-coah.xml` & `123-giata.xml`
* `processor.standalone.xml` The full qualifing file name (without path) of a standalone xml file ie. `1345-coah.xml`
* `processor.standalone.json` The full qualifing file name (without path) of a standalone xml file ie. `234-coah.xml`
* `processor.output.path` The output path for the merged result file ie. `dist/result-file.json`

