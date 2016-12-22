import service.FS;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	try {
			FS.createDirectory("root1");
			System.out.println(FS.getAll());
			FS.createFile("root");
			System.out.println(FS.getAll());
			
			FS.deleteFile("root");
			System.out.println(FS.getAll());
			
			FS.createFile("root1/test");
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    }
}
