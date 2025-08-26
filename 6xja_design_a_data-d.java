import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

class PipelineNode {
    String name;
    List<PipelineNode> dependencies;

    public PipelineNode(String name) {
        this.name = name;
        this.dependencies = new ArrayList<>();
    }
}

public class DataDrivenDevOpsPipelineParser {
    public static List<PipelineNode> parsePipeline(String pipelineConfig) {
        List<PipelineNode> nodes = new ArrayList<>();
        Pattern nodePattern = Pattern.compile("node\\s+(\\w+)\\s*(?:->\\s*(\\w+))?;?");
        Matcher matcher = nodePattern.matcher(pipelineConfig);

        while (matcher.find()) {
            String nodeName = matcher.group(1);
            String dependencyName = matcher.group(2);
            PipelineNode node = new PipelineNode(nodeName);

            if (dependencyName != null) {
                node.dependencies.add(new PipelineNode(dependencyName));
            }

            nodes.add(node);
        }

        return nodes;
    }

    public static void main(String[] args) {
        String pipelineConfig = "node A -> B; node B -> C; node C; node D -> A;";
        List<PipelineNode> nodes = parsePipeline(pipelineConfig);

        for (PipelineNode node : nodes) {
            System.out.println("Node: " + node.name);
            if (!node.dependencies.isEmpty()) {
                System.out.println("  Depends on:");
                for (PipelineNode dependency : node.dependencies) {
                    System.out.println("    " + dependency.name);
                }
            }
        }
    }
}