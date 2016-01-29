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
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist newStylist = new Stylist(request.queryParams("newStylistInput"));
      newStylist.save();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/index.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/stylist/:id", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist newStylist = Stylist.find(Integer.parseInt(request.params("id")));
      model.put("stylist", newStylist);
      model.put("clients", Client.getClientsByStylist(Integer.parseInt(request.params("id"))));
      model.put("template", "templates/stylist.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("template", "templates/newclient.vtl");
      model.put("stylists", Stylist.all());
      model.put("clients", Client.all());
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/newclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client newClient = new Client(request.queryParams("newClientInput"), (Integer.parseInt(request.queryParams("stylistSelection"))));
      newClient.save();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/newclient.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/editpage", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/editstylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistSelection")));
      stylist.update(request.queryParams("newStylist"));
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    post("/editclient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.queryParams("clientSelection")));
      client.update(request.queryParams("newClient"));
      model.put("clients", Client.all());
      model.put("stylists", Stylist.all());
      model.put("template", "templates/edit.vtl");
      return new ModelAndView(model, layout);
    }, new VelocityTemplateEngine());

    get("/deleteSylist", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Stylist stylist = Stylist.find(Integer.parseInt(request.queryParams("stylistSelection")));
      stylist.delete();
      response.redirect("/editpage");
      return null;
    });

    get("/deleteClient", (request, response) -> {
      HashMap<String, Object> model = new HashMap<String, Object>();
      Client client = Client.find(Integer.parseInt(request.queryParams("clientSelection")));
      client.delete();
      response.redirect("/editpage");
      return null;
    });
  }
}
