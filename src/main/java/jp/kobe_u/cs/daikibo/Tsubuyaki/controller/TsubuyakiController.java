package jp.kobe_u.cs.daikibo.Tsubuyaki.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jp.kobe_u.cs.daikibo.Tsubuyaki.entity.Tsubuyaki;
import jp.kobe_u.cs.daikibo.Tsubuyaki.service.TsubuyakiService;

@Controller
public class TsubuyakiController {
    @Autowired  
    TsubuyakiService ts;

    //タイトル画面を表示
    @GetMapping("/")
    String showIndex() {

        return "index";
    }

    //メイン画面を表示
    @GetMapping("/read")
    String showTsubuyakiList(Model model) {
        List<Tsubuyaki> list = ts.getAllTsubuyaki(); //全つぶやきを取得
        model.addAttribute("tsubuyakiList", list);   //モデル属性にリストをセット
        model.addAttribute("tsubuyakiForm", new TsubuyakiForm());  //空フォームをセット
        return "tsubuyaki_list"; //リスト画面を返す
    }

    @GetMapping("/result")
    String showSearchedTsubuyakiList(@ModelAttribute("searchForm") TsubuyakiForm form, Model model) {
        String comment = form.getComment();
        List<Tsubuyaki> list = ts.getTsubuyakiByComment(comment);
        model.addAttribute("searchedList", list);
        model.addAttribute("searchForm", new SearchForm());  //空フォームをセット

        return "result";
    }

    //つぶやきを投稿
    @PostMapping("/read")
    String postTsubuyaki(@ModelAttribute("tsubuyakiForm") TsubuyakiForm form, Model model) {
        //フォームからエンティティに移し替え
        Tsubuyaki t = new Tsubuyaki();
        t.setName(form.getName());
        t.setComment(form.getComment());
        
        //サービスに投稿処理を依頼  
        ts.postTsubuyaki(t);

        return "redirect:/read"; //メイン画面に転送
    } 
}
