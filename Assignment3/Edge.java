public class Edge {
    Vertex v1;
    Vertex v2;
    double weight;
    static int globalCounter = 0;
    int counter = globalCounter++;

    Edge(Vertex v1, Vertex v2, double weight){
        this.v1 = v1;
        this.v2 = v2;
        this.weight = weight;
    }

    Vertex getOtherVertex(Vertex v){
        if(v.equals(v1))
            return v2;
        else if (v.equals(v2))
            return v1;
        else
            return null;
    }

    @Override
    public String toString() {
        String v1Str = (v1 == null ? "NULL" : v1.toString());
        String v2Str = (v2 == null ? "NULL" : v2.toString());
        if(v1Str.compareTo(v2Str) >= 1){
            return v1Str + "<-[" + weight + "]->" + v2Str;
        } else {
            return v2Str + "<-[" + weight + "]->" + v1Str;
        }
    }

    @Override
    public boolean equals(Object obj) {
        boolean result = false;
        try{
            Edge other = (Edge)obj;
            if(other.v1.equals(v2) && other.v2.equals(v1)){
                result = true;
            }

            if(other.v1.equals(v1) && other.v2.equals(v2)){
                result = true;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return result;
    }

    String latexCode(){
        return "\\draw (" +v1.counter + ") -- node[midway, above] {" + weight + "} ("+v2.counter+");";
    }
}
