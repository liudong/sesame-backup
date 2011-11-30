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
package uk.ac.open.kmi.util.backup.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class Configuration {

	private static final Configuration me = new Configuration();

	private static final String PROPERTIES_FILEPATH = "/config.properties";

	private static final String SESAME_SERVER_URI = "sesameServerURL";

	private static final String REPOSITORY_LIST = "repoName";

	private static final String BACKUP_FOLDER_PATH = "bakFolder";

	private String sesameServerUri;

	private String repositoryList;

	private String backupFolderPath;

	public String getSesameServerUri() {
		return sesameServerUri;
	}

	public List<String> getRepositoryList() {
		if (null == repositoryList || "" == repositoryList) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		String[] strings = repositoryList.split(",");
		for ( String repo : strings ) {
			result.add(repo);
		}
		return result;
	}

	public String getBackupFolderPath() {
		return backupFolderPath;
	}

	public static Configuration getInstance() {
		return me;
	}

	private Configuration() {
		Properties properties = new Properties();
		try {
			properties.load(this.getClass().getResourceAsStream(PROPERTIES_FILEPATH));
		} catch (IOException e) {
			e.printStackTrace();
		}
		sesameServerUri = properties.getProperty(SESAME_SERVER_URI);
		repositoryList = properties.getProperty(REPOSITORY_LIST);
		backupFolderPath = properties.getProperty(BACKUP_FOLDER_PATH);
	}

	public static void main(String[] args) {
		Configuration config = Configuration.getInstance();
		System.out.println(config.getSesameServerUri());
		System.out.println(config.getBackupFolderPath());
		config.getRepositoryList();
	}
}
