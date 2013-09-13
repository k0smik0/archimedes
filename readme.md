#Archimedes

Archimedes is a little ORM framework that I developed for a university exam, in 2009.  
  

It is a ORM as Hibernate, allowing data persistence with HSQLDB.  
Of course, it is just for an educational purpose.  
  
You have just to annotate your fields: attribute "position" is mandatory, because Archimedes need it in order to find the exact column (attribute, in SQL world) for that field
  
    
<pre>
<code>
public class Concert {
		
		@HSQLDB(position=1)
		private int concertCode;
		
		@HSQLDB(position=2)
		private String groupName;
		
		@HSQLDB(position=3)
		private int concertDate;

// accessors
}
</code>
</pre>

<pre>
<code>
public class FestivalService {

	public boolean insert(Concert c) {
		
		EGDAO<Concert> h = ArchimedesService.INSTANCE.getDAO();
				
		Collection<Concert> c1 = h.read("groupName", c.getGroupName()); 
						
		Collection<Concert> c2 = h.read("ConcertCode", c.getConcertCode());
								
		if (c1 == null && c2 == null) {
			boolean r = h.create(c);
			return r;
		}
			
		return false;
	}
}
</code>
</pre>

A full example is available [here](../../kw_archimedes_kv_optimusprime__sample)