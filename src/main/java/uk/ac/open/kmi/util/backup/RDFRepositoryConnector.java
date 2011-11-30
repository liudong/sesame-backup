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

import org.openrdf.rdf2go.RepositoryModel;
import org.openrdf.repository.Repository;
import org.openrdf.repository.RepositoryException;
import org.openrdf.repository.http.HTTPRepository;

public class RDFRepositoryConnector {

	private Repository sesameRepository;

	public RDFRepositoryConnector(String serverUri, String repositoryName) throws RepositoryException {
		if ( null == serverUri || null == serverUri.toString() || "" == serverUri.toString() ||
				null == repositoryName || "" == repositoryName ) {
			return;
		}
		sesameRepository  = new HTTPRepository(serverUri.toString(), repositoryName);
		sesameRepository.initialize();
	}

	public RepositoryModel openRepositoryModel() {
		if ( null == sesameRepository ) {
			return null;
		}
		RepositoryModel result = new RepositoryModel(sesameRepository);
		if ( result != null ) {
			result.open();
		}
		return result;
	}

	public void closeRepositoryModel(RepositoryModel modelToClose) {
		if ( modelToClose != null ) {
			modelToClose.close();
		}
	}

	public HTTPRepository getRepository() {
		return (HTTPRepository) sesameRepository;
	}

}
