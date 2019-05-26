package com.denis.test.server.controller;
import com.denis.test.server.entity.*;
import com.denis.test.server.other.Constants;
import com.denis.test.server.other.Functions;
import com.denis.test.server.service.PortfolioService;
import com.denis.test.server.service.UserService;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RestController
public class PortfolioController {
    @Autowired
    private PortfolioService portfolioService;

    @Autowired
    private UserService userService;


    private boolean userVerification(List<Map<String,Object>> allParams){
        if (allParams.isEmpty())return false;
        String username = allParams.get(0).get("username").toString();
        String token = allParams.get(0).get("token").toString();

        return userVerification(username, token);
    }

    private boolean userVerification(String username, String token){
        if(username == null || token == null) return false;
        UserEntity userEntity = userService.findByUsername(username);
        return userEntity.getToken().equals(token);
    }


    @RequestMapping(value = "/portfolio/{id}", method = RequestMethod.GET)
    @ResponseBody
    public PortfolioEntity getPortfolio(@PathVariable("id") int portfolioID){
        return portfolioService.getPortfolioById(portfolioID); }


    @RequestMapping(value = "/pf/{username}", method = RequestMethod.GET)
    @ResponseBody
    public List<PortfolioEntity> getPortfolio(@PathVariable("username") String username){
        return portfolioService.getListByUsername(username);}




    @RequestMapping(value = "/categories", method = RequestMethod.GET)
    @ResponseBody
    public List<PortfolioCategoryEntity> getCategories(){
        return Functions.removeDublicates(portfolioService.getCategoriesList());}

    @RequestMapping(value = "/criteria/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<PortfolioCriterionEntity> getCriteria(@PathVariable("id") int id){
        return Functions.removeDublicates(portfolioService.getCriteriaListByCategoryId(id));}

    @RequestMapping(value = "/types/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<PortfolioTypeEntity> getTypes(@PathVariable("id") int id){
        return Functions.removeDublicates(portfolioService.getTypesListByCategoryId(id));}



    @RequestMapping(value = "/portfolio/files/post", method = RequestMethod.POST)
    @ResponseBody
    public String addFiles(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            PortfolioFileEntity file = PortfolioFileEntity.CreateObjectFromMap(allParams.get(1));

            String resultSrc =  UUID.randomUUID().toString() + "_" + file.getFile_name();
            file.setFile_src(resultSrc);
            portfolioService.addFileToPortfolio(file);
            return resultSrc;
        }
        return null;
    }


    @RequestMapping(value = "/portfolio/files/get", method = RequestMethod.POST)
    @ResponseBody
    public Set<PortfolioFileEntity> getFiles(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            int portfolioID = (int)allParams.get(1).get("id_portfolio");
            return portfolioService.getFilesByPortfolioId(portfolioID);
        }
        return null;
    }


    @RequestMapping(value = "/portfolio/files/load",params = {"username", "token", "src"}, method = RequestMethod.GET)
    public void loadFiles(HttpServletResponse response,
                          @RequestParam(value = "username") String username,
                          @RequestParam(value = "token") String token,
                          @RequestParam(value = "src") String src) throws IOException {
        if(userVerification(username,token)){
            File file = new File(Constants.URL.UPLOADS_DOCS + src);
            InputStream in = new FileInputStream(file);
            response.setContentType(MediaType.ALL_VALUE);
            IOUtils.copy(in, response.getOutputStream());
        }
    }


    @RequestMapping(value = "/portfolio/upload", method = RequestMethod.POST)
    public String uploadFiles(@RequestParam String username,
                             @RequestParam String token, @RequestParam("file") MultipartFile file) {
        if (file!=null){
            if(userVerification(username,token)){
                File uploadDir = new File(Constants.URL.UPLOADS_DOCS);
                if(!uploadDir.exists()){
                    uploadDir.mkdir();
                }
                try {
                    file.transferTo(new File(Constants.URL.UPLOADS_DOCS + file.getOriginalFilename()));
                    return "Файл " + file.getOriginalFilename() + " успешно загружен";
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Ошибка загрузки";
                }
            }
        }
        return "Ошибка загрузки";
    }



    @RequestMapping(value = "/portfolio/list", method = RequestMethod.POST)
    @ResponseBody
    public List<PortfolioEntity> getList(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            return portfolioService.getListByUsername(allParams.get(0).get("username").toString());
        }
        return null;
    }

    @RequestMapping(value = "/users/portfolio/list", method = RequestMethod.POST)
    @ResponseBody
    public List<PortfolioEntity> getListByUser(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)){
            String name = allParams.get(1).get("name").toString();
            String faculty = allParams.get(1).get("faculty").toString();
            String group = allParams.get(1).get("group").toString();
            UserEntity userEntity = userService.findByAnotherData(name,faculty,group);

            return portfolioService.getListByUsername(userEntity.getUsername());
        }
        return null;
    }



    @RequestMapping(value = "/portfolio/add", method = RequestMethod.POST)
    @ResponseBody
    public Integer addPortfolio(
            @RequestBody List<Map<String,Object>> allParams){

        if( userVerification(allParams)) {
            UserEntity author = userService.findByUsername(allParams.get(0).get("username").toString());
            PortfolioCategoryEntity category = portfolioService.getCategoryById((int)allParams.get(1).get("id_category"));
            PortfolioCriterionEntity criterion = portfolioService.getCriterionById((int)allParams.get(1).get("id_criterion"));
            PortfolioTypeEntity type = portfolioService.getTypeById((int)allParams.get(1).get("id_type"));
            PortfolioEntity portfolioEntity = PortfolioEntity.CreateObjectFromMap(allParams.get(1));
            if(portfolioEntity!=null){
                portfolioEntity.setAuthor(author);
                portfolioEntity.setCategory(category);
                portfolioEntity.setCriterion(criterion);
                portfolioEntity.setType(type);
                portfolioService.savePortfolio(portfolioEntity);
                return portfolioEntity.getId_portfolio();
            }
        } return null;
    }


    @RequestMapping(value = "/portfolio/update", method = RequestMethod.POST)
    @ResponseBody
    public Integer updatePortfolio(@RequestBody List<Map<String,Object>> allParams){

        if(userVerification(allParams)) {
            int portfolioID = (int)allParams.get(1).get("id_portfolio");
            PortfolioEntity oldPortfolio = portfolioService.getPortfolioById(portfolioID);
            Set<PortfolioFileEntity> oldFiles = oldPortfolio.getFiles();

            UserEntity author = userService.findByUsername(allParams.get(0).get("username").toString());
            PortfolioEntity portfolioEntity = PortfolioEntity.CreateObjectFromMap(allParams.get(1));

            if(portfolioEntity!=null){
                portfolioEntity.setId_portfolio((portfolioID));
                portfolioEntity.setAuthor(author);
                portfolioEntity.setCategory(portfolioService.getCategoryById((int)allParams.get(1).get("id_category")));
                portfolioEntity.setCriterion(portfolioService.getCriterionById((int)allParams.get(1).get("id_criterion")));
                portfolioEntity.setType(portfolioService.getTypeById((int)allParams.get(1).get("id_type")));
                portfolioEntity.setFiles(null);

                portfolioService.savePortfolio(portfolioEntity);

                for (Iterator<PortfolioFileEntity> it = oldFiles.iterator(); it.hasNext(); ) {
                    portfolioService.removeFile(it.next());
                }
                //Очистить на диске
                return portfolioEntity.getId_portfolio();
            }
        } return null;
    }




    @RequestMapping(value = "/portfolio/delete", method = RequestMethod.POST)
    @ResponseBody
    public Integer deetePortfolio(@RequestBody List<Map<String,Object>> allParams){
        if(userVerification(allParams)) {
            int portfolioID = (int)allParams.get(1).get("id_portfolio");
            PortfolioEntity portfolio = portfolioService.getPortfolioById(portfolioID);

            if(portfolio.getAuthor().getUsername().equals(allParams.get(0).get("username"))){
                Set<PortfolioFileEntity> files = portfolio.getFiles();
                portfolioService.removePortfolio(portfolioID);
                for (PortfolioFileEntity file : files) {
                    portfolioService.removeFile(file);
                }
                return portfolioID;
            }
        }
        return null;
    }






    @RequestMapping(value = "/init", method = RequestMethod.GET)
    @ResponseBody
    public String initData(){

        FacultyEntity fc1, fc2;
        GroupEntity gr1, gr2, gr3;

        fc1 = new FacultyEntity("ФИТ","Факультет информационных технологий");
        fc2 = new FacultyEntity("ФИМ","Факультет мемов");
        gr1 = new GroupEntity("15ИВТ-1","15 Информатика и вычислительная техника - 1");
        gr2 = new GroupEntity("15ИВТ-2","15 Информатика и вычислительная техника - 2");
        gr3 = new GroupEntity("15МЕМ","15 Мемология");

        userService.saveFaculties(Arrays.asList(fc1,fc2));
        userService.saveGroups(Arrays.asList(gr1,gr2,gr3));

        userService.addGroups(fc1,Arrays.asList(gr1,gr2));
        userService.addGroups(fc2,Arrays.asList(gr3));


        PortfolioCategoryEntity ca1, ca2, ca3, ca4, ca5, ca6, ca7;
        PortfolioCriterionEntity cr1, cr2, cr3, cr4, cr5, cr6, cr7, cr8, cr9, cr10, cr11, cr12, cr13, cr14, cr15, cr16;
        PortfolioTypeEntity tp1, tp2, tp3;


        ca1 = new PortfolioCategoryEntity("Научно-исследовательская деятельность");
        ca2 = new PortfolioCategoryEntity("Учебная деятельность");
        ca3 = new PortfolioCategoryEntity("Общественная деятельность");
        ca4 = new PortfolioCategoryEntity("Культурно-творческая деятельность");
        ca5 = new PortfolioCategoryEntity("Спортивная деятельность");
        ca6 = new PortfolioCategoryEntity("Курсовая работа (проект)");
        ca7 = new PortfolioCategoryEntity("Дипломная работа (проект)");


        cr1 = new PortfolioCriterionEntity("получение студентом в течение года, предшествующего назначению " +
                "повышенной государственной академической стипендии");
        cr2 = new PortfolioCriterionEntity("наличие у студента публикации в научном (учебно-научном, " +
                "учебно-методическом) издании ");
        cr3 = new PortfolioCriterionEntity("получение студентом в течение не менее 2-х следующих друг за " +
                "другом промежуточных аттестаций, только оценок \"отлично\"");
        cr4 = new PortfolioCriterionEntity("получение студентом в течение года награды (приза) за " +
                "результаты проектной деятельности и (или) опытно-конструкторской работы");
        cr5 = new PortfolioCriterionEntity("признание студента победителем или призером международной, " +
                "всероссийской, ведомственной или региональной олимпиады, конкурса, соревнования, состязания");
        cr6 = new PortfolioCriterionEntity("систематическое участие студента в течение года в проведении " +
                "(обеспечении проведения) общественно значимой деятельности социального, культурного, правозащитного, " +
                "общественно полезного характера");
        cr7 = new PortfolioCriterionEntity("систематическое участие студента в течение года в деятельности " +
                "по информационному обеспечению общественно значимых мероприятий, общественной жизни государственной " +
                "образовательной организации");
        cr8 = new PortfolioCriterionEntity("получение студентом в течение года награды (приза) за " +
                "результаты культурно-творческой деятельности");
        cr9 = new PortfolioCriterionEntity("публичное представление студентом в течение года созданного " +
                "им произведения литературы или искусства");
        cr10 = new PortfolioCriterionEntity("систематическое участие студента в течение года в проведении " +
                "(обеспечении проведения) публичной культурно-творческой деятельности воспитательного " +
                "характера");
        cr11 = new PortfolioCriterionEntity("получение студентом в течение года награды (приза) за " +
                "результаты спортивной деятельности, осуществленной им в рамках спортивных  мероприятий");
        cr12 = new PortfolioCriterionEntity("систематическое участие студента в течение года в спортивных " +
                "мероприятиях воспитательного, пропагандистского характера");
        cr13 = new PortfolioCriterionEntity("выполнение нормативов и требований золотого знака отличия " +
                "\"Всероссийского физкультурно-спортивного комплекса (ГТО) соответствующей возрастной группы");
        cr14 = new PortfolioCriterionEntity("Сдано на \"Отлично\"");
        cr15 = new PortfolioCriterionEntity("Сдано на \"Хорошо\"");
        cr16 = new PortfolioCriterionEntity("Сдано на \"Удовлетворительно\"");


        tp1 = new PortfolioTypeEntity("Организатор");
        tp2 = new PortfolioTypeEntity("Участник");
        tp3 = new PortfolioTypeEntity("Автор");


        portfolioService.saveCategories(Arrays.asList(ca1,ca2,ca3,ca4,ca5,ca6,ca7));
        portfolioService.saveCriteria(Arrays.asList(cr1,cr2,cr3,cr4,cr5,cr6,cr7,cr8,cr9,cr10,cr11,cr12,cr13,cr14,cr15,cr16));
        portfolioService.saveTypes(Arrays.asList(tp1,tp2,tp3));


        portfolioService.addCriteria(ca1,Arrays.asList(cr1,cr2));
        portfolioService.addCriteria(ca2,Arrays.asList(cr3,cr4,cr5));
        portfolioService.addCriteria(ca3,Arrays.asList(cr6,cr7));
        portfolioService.addCriteria(ca4,Arrays.asList(cr8,cr9,cr10));
        portfolioService.addCriteria(ca5,Arrays.asList(cr11,cr12,cr13));
        portfolioService.addCriteria(ca6,Arrays.asList(cr14,cr15,cr16));
        portfolioService.addCriteria(ca7,Arrays.asList(cr14,cr15,cr16));


        portfolioService.addTypes(ca1, Arrays.asList(tp1,tp2));
        portfolioService.addTypes(ca2, Arrays.asList(tp1,tp2));
        portfolioService.addTypes(ca3, Arrays.asList(tp1,tp2));
        portfolioService.addTypes(ca4, Arrays.asList(tp1,tp2));
        portfolioService.addTypes(ca5, Arrays.asList(tp1,tp2));
        portfolioService.addTypes(ca6, Arrays.asList(tp3));
        portfolioService.addTypes(ca7, Arrays.asList(tp3));




        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("admin");
        userEntity.setName("Фадеев Денис");
        userEntity.setPassword("admin");
        userEntity.setLevel("Студент");
        userEntity.setFaculty(userService.getFacultyById(1));
        userEntity.setGroup(userService.getGroupById(1));
        userEntity.setPhone("+79529616552");
        userEntity.setMail("whitehydra@yandex.ru");
        userEntity.setInfo("Пробная страница");
        userService.save(userEntity);








        return "Data initialized";
    }
}
