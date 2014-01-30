import java.io.File;
import java.io.IOException;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.ProcessingResource;
import gate.creole.ANNIEConstants;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;
import gate.util.persistence.PersistenceManager;


public class MLsample {

	public static void main(String[] args)
	{
		try {
			gate.Gate.init();
			System.out.println("Gate initialized");
			
			File pluginsHome = new File(Gate.getPluginsHome(), ANNIEConstants.PLUGIN_DIR);
			File annieGapp = new File(pluginsHome, ANNIEConstants.DEFAULT_FILE);
			SerialAnalyserController controller = (SerialAnalyserController)PersistenceManager.loadObjectFromFile(annieGapp);
			System.out.println("Annie initialized");
			
			Gate.getCreoleRegister().registerDirectories(new File( Gate.getPluginsHome(), ANNIEConstants.PLUGIN_DIR ).toURI().toURL());
			FeatureMap params = Factory.newFeatureMap();
			ProcessingResource tagger = (ProcessingResource)Factory.createResource("gate.creole.POSTagger", params);
			System.out.println("POSTagger plugin loaded");
			
			File mlPlugins = new File(Gate.getPluginsHome(), "Learning");
			Gate.getCreoleRegister().registerDirectories(mlPlugins.toURI().toURL());
			
			File config = new File(mlPlugins, "test/sentence-classification/engines-knnweka.xml");
			FeatureMap mlModel = Factory.newFeatureMap();
			mlModel.put("configFileURL", config.toURI().toURL());
			ProcessingResource learning = (ProcessingResource)Factory.createResource("gate.learning.LearningAPIMain", mlModel);
			System.out.println("Learning plugin loaded");
			
		} catch (GateException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
