import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylists newStylist = new Stylists(request.queryParams("newStylistInput"));
      newStylist.save();
      model.put("stylists", Stylists.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newclient.vtl");
      model.put("stylists", Stylists.all());
      model.put("client", Clients.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylists newStylist = Stylists.find(Integer.parseInt(request.params("id")));
      model.put("stylist", newStylist);
      model.put("clients", Clients.all());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Clients newClient = new Clients(request.queryParams("newClientInput"), (Integer.parseInt(request.queryParams("stylistSelection"))));
      newClient.save();
      model.put("stylists", Stylists.all());
      model.put("clients", Clients.all());
      model.put("template", "templates/newclient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

  }
}
