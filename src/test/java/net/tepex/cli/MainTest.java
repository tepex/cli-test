package net.tepex.cli;

//import static org.junit.Assert.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.*;

import org.junit.Test;

import java.text.DecimalFormat;

import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

public class MainTest
{
	@Test
	public void testInitParams()
	{
		String params = "asdf";
		String[] args = params.split("\\s+");
		assertThat(Main.initParams(args), is(false));
		params = "-encoding";
		args = params.split("\\s+");
		assertThat(Main.initParams(args), is(false));
		params = "-encoding aaa";
		args = params.split("\\s+");
		assertThat(Main.initParams(args), is(true));
	}
	
	@Test
	public void testCommands()
	{
		assertThat(Command.parseCommand("kmlkm"), nullValue());
		
		Command test = new Command(Command.CMD_WRITE, "qqq");
		assertThat(Command.parseCommand("write qqq"), equalTo(test));
		
		test = new Command(Command.CMD_READ, null);
		assertThat(Command.parseCommand("read"), equalTo(test));
		
		String line = "write whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line whery big line";
		assertThat(Main.checkInputLine(line), is(false));
	}
	
	@Test
	public void testBaseMsk()
	{
		/*
		assertThat(new Float(Location.GRADUS), is(0.01745329251994F));
		// BASE
		assertThat(new Float(Location.BASE_LAT), is(0.00000899321605919F));
		// MSK
		double radMsk = Location.CENTER_LATITUDE * Location.GRADUS;
		double cosRadMsk = Math.cos(radMsk);
		double msk = 1 / cosRadMsk;
		log.debug("msk: "+msk);
		log.debug("1 / cos(msk) = "+(1 / Math.cos(Location.CENTER_LATITUDE * Location.GRADUS)));
		assertThat(new Float(msk), is(1.7769114154238F));
			
		double baseMsk = Location.getBaseLat(Location.CENTER_LATITUDE);
		log.debug("baseMsk = "+baseMsk);
		assertThat(new Float(baseMsk), is(0.0000159801482772F));
		*/
	}
	
	@Test
	public void testLongitudeDistance()
	{
		/*
		double ln = Location.convertMskLongitudeDistance(300000);
		log.debug("ln: "+DF.format(ln));
		long lnInt = Math.round(ln*10);
		assertThat(lnInt, is(48L));
		*/
	}
	
	@Test
	public void testLatitudeDistance()
	{
		/*
		double lt = Location.convertLatitudeDistance(300000);
		log.debug("lt: "+DF.format(lt));
		long ltInt = Math.round(lt*10);
		assertThat(ltInt, is(27L));
		*/
	}
	
	private static final Logger log = getLogger(MainTest.class);
	public static final DecimalFormat DF = new DecimalFormat("##.######");
}
 