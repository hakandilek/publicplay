package test;

import play.Logger;
import play.Logger.ALogger;
import play.api.libs.Files;
import play.test.FakeApplication;

import com.avaje.ebean.Ebean;

public class IntegrationTestHelper {

	private static ALogger log = Logger.of(IntegrationTestHelper.class);

	public EvolutionScript evolutionScript(FakeApplication app) {
		return evolutionScript(app, "conf/evolutions/integrationTest/data.sql");
	}

	public EvolutionScript evolutionScript(FakeApplication app, String script) {

		// Reading the evolution file
		play.api.test.FakeApplication wrappedApplication = app.getWrappedApplication();
		String evolutionContent = Files.readFile(wrappedApplication.getFile(script));

		// Splitting the String to get Create & Drop DDL
		String[] splittedEvolutionContent = evolutionContent.split("# --- !Ups");
		String[] upsDowns = splittedEvolutionContent[1].split("# --- !Downs");
		String ups = upsDowns[0];
		String downs = upsDowns[1];

		return new EvolutionScript(ups, downs);
	}

	public void runUps(EvolutionScript script) {
		if (script != null && script.ups != null) {
			Ebean.execute(Ebean.createCallableSql(script.ups));
		}
	}

	public void runDowns(EvolutionScript script) {
		if (script != null && script.downs != null) {
			Ebean.execute(Ebean.createCallableSql(script.downs));
		}
	}

	public void runUps(FakeApplication app, String script) {
		runUps(evolutionScript(app, script));
	}

	public void runDowns(FakeApplication app, String script) {
		runDowns(evolutionScript(app, script));
	}

	public void runUps(FakeApplication app) {
		runUps(evolutionScript(app));
	}

	public void runDowns(FakeApplication app) {
		runDowns(evolutionScript(app));
	}

	public void reset(FakeApplication app, String script) {
		EvolutionScript scr = evolutionScript(app, script);
		try {
			runDowns(scr);
		} catch (Exception e) {
			log.debug("exception : ", e);
		}
		try {
			runUps(scr);
		} catch (Exception e) {
			log.debug("exception : ", e);
		}
	}

	public void reset(FakeApplication app) {
		EvolutionScript scr = evolutionScript(app);
		try {
			runDowns(scr);
		} catch (Exception e) {
			log.debug("exception : ", e);
		}
		try {
			runUps(scr);
		} catch (Exception e) {
			log.debug("exception : ", e);
		}
	}

	class EvolutionScript {

		String ups;
		String downs;

		public EvolutionScript(String ups, String downs) {
			this.ups = ups;
			this.downs = downs;
		}

	}

}
