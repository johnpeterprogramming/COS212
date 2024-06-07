
public class UndirectedGraph<T> extends Graph<T> {

    public UndirectedGraph(GraphImplementation<T> impl) {
        super(impl);
    }

    @Override
    public void addEdge(T from, T to) {
        impl.addEdge(from, to);
        impl.addEdge(to, from);
    }

    @Override
    public void removeEdge(T from, T to) {
        impl.removeEdge(from, to);
        impl.removeEdge(to, from);
    }
}
