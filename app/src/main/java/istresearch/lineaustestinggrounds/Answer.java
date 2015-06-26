package istresearch.lineaustestinggrounds;

/**
 * Created by Titch on 6/25/2015.
 */
public class Answer extends Entity
{
    private String answer;

    public Answer(String a)
    {
        answer = a;
    }

    public void setAnswer(String a)
    {

    }

    /*@Override
    public void sendLink(Entity e)
    {
        Link<Entity, Entity> l = new Link<Entity, Entity>(this, e);
        linksFromThisNode.add(l);
    }*/
}
