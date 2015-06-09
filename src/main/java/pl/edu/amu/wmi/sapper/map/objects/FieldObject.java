package pl.edu.amu.wmi.sapper.map.objects;


import org.codehaus.jackson.annotate.JsonSubTypes;
import org.codehaus.jackson.annotate.JsonSubTypes.Type;
import org.codehaus.jackson.annotate.JsonTypeInfo;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type")
@JsonSubTypes({
        @Type(value = Blockade.class, name = "Blockade"),
        @Type(value = Empty.class, name = "Empty"),
        @Type(value = Bomb.class, name = "Bomb"),
        @Type(value = Sapper.class, name = "Sapper"),
        @Type(value = Civilians.class, name = "Civilians")
})
public abstract class FieldObject {
	
}
