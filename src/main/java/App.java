import java.util.Map;
import java.util.HashMap;
import static spark.Spark.*;
import spark.ModelAndView;
import spark.template.velocity.VelocityTemplateEngine;

public class App {

  public static void main(String[] args) {
    ProcessBuilder process = new ProcessBuilder();
     Integer port;
     if (process.environment().get("PORT") != null) {
         port = Integer.parseInt(process.environment().get("PORT"));
     } else {
         port = 4567;
     }

    staticFileLocation("/public");
    String layout = "templates/layout.vtl";

    get("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/deleteSylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Stylists stylist = Stylists.find(Integer.parseInt(request.queryParams("stylistSelection")));
      stylist.delete();
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylists newStylist = new Stylists(request.queryParams("newStylistInput"));
      newStylist.save();
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/deleteClient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Clients client = Clients.find(Integer.parseInt(request.queryParams("clientSelection")));
      client.delete();

      model.put("clients", Clients.all());
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newclient.vtl");
      model.put("stylists", Stylists.all());
      model.put("clients", Clients.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylists newStylist = Stylists.find(Integer.parseInt(request.params("id")));
      model.put("stylist", newStylist);
      model.put("clients", Clients.getClientsByStylist(Integer.parseInt(request.params("id"))));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Clients newClient = new Clients(request.queryParams("newClientInput"), (Integer.parseInt(request.queryParams("stylistSelection"))));
      newClient.save();
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/newclient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/edit", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Stylists stylist = Stylists.find(Integer.parseInt(request.queryParams("stylistSelection")));

      stylist.update(request.queryParams("newStylist"));
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/editclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();

      Clients client = Clients.find(Integer.parseInt(request.queryParams("clientSelection")));

      client.update(request.queryParams("newClient"));
      model.put("clients", Clients.all());
      model.put("stylists", Stylists.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());
  }
}
