package net.tepex.cli;

public class Command
{
	Command(String cmd, String data)
	{
		this.cmd = cmd;
		this.data = data;
	}
	
	public static Command parseCommand(String src)
	{
		if(src.equals(CMD_QUIT) || src.equals(CMD_READ)) return new Command(src, null);
		else if(src.startsWith(CMD_WRITE+" ")) return new Command(CMD_WRITE, src.substring(CMD_WRITE.length()+1).trim());
		return null;
	}
	
	public boolean isWrite()
	{
		return cmd.equals(CMD_WRITE);
	}
	
	public boolean isRead()
	{
		return cmd.equals(CMD_READ);
	}
	
	public boolean isQuit()
	{
		return cmd.equals(CMD_QUIT);
	}
	
	public String getData()
	{
		return data;
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(!(obj instanceof Command)) return false;
		Command other = (Command)obj;
		if(!cmd.equals(other.cmd)) return false;
		if(cmd.equals(CMD_WRITE))
		{
			if(data == null && other.data == null) return true;
			if(data != null && other.data == null) return false;
			return data.equals(other.data);
		}
		return true;
	}
	
	private String cmd;
	private String data;
	
	public static final String CMD_WRITE = "write";
	public static final String CMD_READ = "read";
	public static final String CMD_QUIT = "quit";
}