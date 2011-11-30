/*
   Copyright ${year}  Knowledge Media Institute - The Open University

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/
package uk.ac.open.kmi.util.backup;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.ontoware.rdf2go.exception.ModelRuntimeException;
import org.ontoware.rdf2go.model.Syntax;
import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.RepositoryException;

import uk.ac.open.kmi.util.backup.config.Configuration;

public class SesameBackup {

	private String sesameServerUri;

	private List<String> repoList;

	private String bakFolderPath;

	public SesameBackup(Configuration config) {
		sesameServerUri = config.getSesameServerUri();
		repoList = config.getRepositoryList();
		bakFolderPath = config.getBackupFolderPath();
	}

	public void backupNow() throws RepositoryException, ModelRuntimeException, IOException {
		for ( String repoName : repoList ) {
			backupOneRepository(repoName);
		}
	}

	public static void main(String[] args) {
		Configuration config = Configuration.getInstance();
		SesameBackup backup = new SesameBackup(config);
		try {
			backup.backupNow();
		} catch (RepositoryException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (ModelRuntimeException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void backupOneRepository(String repoName) throws RepositoryException, ModelRuntimeException, IOException {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String fileName = repoName + "." + dateFormat.format(new Date()) + ".trig";
		System.out.println(bakFolderPath + fileName);
		RDFRepositoryConnector connector = new RDFRepositoryConnector(sesameServerUri, repoName);
		RepositoryModel model = connector.openRepositoryModel();
		File file = new File(bakFolderPath + fileName);
		FileOutputStream os = new FileOutputStream(file);
		model.writeTo(os, Syntax.Trig);
		os.close();
		connector.closeRepositoryModel(model);
	}

}
