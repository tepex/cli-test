package net.tepex.cli;

import org.slf4j.Logger;

import java.io.InputStreamReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;

import java.util.ArrayList;
import java.util.List;
import static org.slf4j.LoggerFactory.getLogger;

public class Main
{
	public static final void main(String[] args)
	{
		log.info("Start command processor");
		if(args.length > 0 && !initParams(args))
		{
			log.error("initParams error! Exit.");
			return;
		}
		
		LineNumberReader reader;
		try
		{
			InputStreamReader isr;
			if(encoding != null) isr = new InputStreamReader(System.in, encoding);
			else isr = new InputStreamReader(System.in);
			reader = new LineNumberReader(isr);
		}
		catch(IOException e)
		{
			if(e instanceof UnsupportedEncodingException)
			{
				System.err.println("Unsupported encoding "+encoding+"! Exit.");
			}
			else
			{
				System.err.println("System I/O error! Exit.");
			}
			log.error("System.in error!", e);
			return;
		}
		
		while(true)
		{
			System.out.print("Enter command:\n>");
			Command cmd;
			try
			{
				cmd = getCommand(reader);
			}
			catch(IOException e)
			{
				log.error("Exception reading command!", e);
				System.err.println("Command can't be read du system error! Exit.");
				try
				{
					reader.close();
				}
				catch(IOException e1)
				{
					log.error("Can't close reader!", e1);
				}
				return;
			}
			if(cmd == null)
			{
				System.err.println("Unknown command! ["+Command.CMD_WRITE+", "+Command.CMD_READ+", "+Command.CMD_QUIT+"]");
			}
			else if(cmd.isQuit())
			{
				System.out.println("Bye!");
				log.info("Bye!");
				try
				{
					reader.close();
				}
				catch(IOException e)
				{
					log.error("Can't close reader!", e);
				}
				return;
			}
			else if(cmd.isRead())
			{
				if(buffer.size() == 0)
				{
					log.info("Buffer is empty.");
					System.out.println("No data in buffer!");
				}
				else System.out.println(buffer.get(buffer.size()-1));
			}
			else if(cmd.isWrite())
			{
				String data = cmd.getData();
				if(checkInputLine(data))
				{
					buffer.add(data);
					log.info("Writing new data to buffer: \""+data+"\". Buffer size="+buffer.size());
					System.out.println("New data saved.");
				}
			}
			System.out.println();
		}
		
		
		
	}
	
	/**
	 * Возвращает введенную команду пользователем.
	 *
	 * @param reader источник чтения.
	 * @return команда пользователя или null, если некорректная команда.
	 */
	static Command getCommand(LineNumberReader reader) throws IOException
	{
		String line = reader.readLine();
		if(line == null) return null;
		return Command.parseCommand(line);
	}
	
	/**
	 * Проверка строки на допустимую максимальную длину.
	 *
	 * @param line проверяемая строка
	 * @return true - проверка успешна, false - строка длинее допустимой максимальной длины.
	 */
	static boolean checkInputLine(String line)
	{
 		if(line.length() > maxLineLength)
		{
			System.err.println("Entered line is to long. Line length must be < "+maxLineLength);
			log.warn("Entered line ["+line+"] > "+maxLineLength);
			return false;
		}
		return true;
	}
	
	/**
	 * Инициализация парамеров командной строки.
	 *
	 * @param args параметры командной строки.
	 * @return true - если все ОК, false - некорректные значения параметров.
	 */
	static boolean initParams(String[] args)
	{
		for(int i = 0; i < args.length; ++i)
		{
			if(args[i].equals(PARAM_ENCODING))
			{
				encoding = getParam(args, i, PARAM_ENCODING);
				if(encoding == null) return false;
				++i;
				log.info("param "+PARAM_ENCODING+" = "+encoding);
			}
			else if(args[i].equals(PARAM_MAX))
			{
				String maxStr = getParam(args, i, PARAM_MAX);
				if(maxStr == null) return false;
				try
				{
					maxLineLength = Integer.parseInt(maxStr);
				}
				catch(NumberFormatException e)
				{
					System.err.println("Param "+PARAM_MAX+" not a number!");
					return false;
				}
				++i;
				log.info("param "+PARAM_MAX+" = "+maxLineLength);
			}
			else
			{
				log.error("Unknown param "+args[i]);
				System.err.println("Unknown param "+args[i]);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Возвращает значение параметра командной строки или null, в случае ошибки.
	 * 
	 * @param args параметры командной строки.
	 * @param i индекс проверяемого параметра в массиве параметров.
	 * @param paramName имя проверяемого параметра.
	 * @return значение параметра или null, если ошибка.
	 */
	private static String getParam(String[] args, int i, String paramName)
	{
		if(i == (args.length-1))
		{
			System.err.println("Empty "+paramName+" param!");
			return null;
		}
		return args[i+1];
	}

	public static final String PARAM_ENCODING = "-encoding";
	public static final String PARAM_MAX = "-max";
	
	public static final String DEFAULT_ENCODING = "utf-8";
	public static final int MAX_LINE_LENGTH = 255;
	
	
	private static final Logger log = getLogger(Main.class);

	private static List<String> buffer = new ArrayList<String>();
	private static String encoding = DEFAULT_ENCODING;
	private static int maxLineLength = MAX_LINE_LENGTH;
	
}