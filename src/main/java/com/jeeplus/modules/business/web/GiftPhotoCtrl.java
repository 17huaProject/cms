package com.jeeplus.modules.business.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jeeplus.common.persistence.Page;
import com.jeeplus.common.utils.StringUtils;
import com.jeeplus.common.web.BaseController;
import com.jeeplus.modules.business.entity.GiftPhoto;
import com.jeeplus.modules.business.service.GiftPhotoService;

@Controller
@RequestMapping(value = "${adminPath}/cms/giftPhoto")
public class GiftPhotoCtrl   extends BaseController {

	@Autowired
	GiftPhotoService giftPhotoPhotoService;
	

	@RequiresPermissions("cms:giftPhoto:index")
	@RequestMapping("index")
	public String index(){
		return "modules/gift/giftPhotoIndex";
	}
	
	@RequiresPermissions("cms:giftPhoto:index")
	@RequestMapping(value = {"list", ""})
	public String list(HttpServletRequest request, HttpServletResponse response, Model model) {
		List<GiftPhoto> giftPhotos = giftPhotoPhotoService.findGiftPhotos();
        model.addAttribute("giftPhotos", giftPhotos);
		return "modules/gift/giftPhotoList";
	}
	
	
	@RequiresPermissions(value={"cms:giftPhoto:edit"})
	@RequestMapping(value = "form")
	public String form(@RequestParam(required=false) Integer id, Model model) {
		GiftPhoto giftPhoto = giftPhotoPhotoService.getGiftPhoto(id);
		model.addAttribute("giftPhoto", giftPhoto);
		return "modules/gift/giftPhotoForm";
	}
	
	
	@RequiresPermissions(value={"cms:giftPhoto:view"})
	@RequestMapping(value = "viewForm")
	public String viewForm(@RequestParam(required=false) Integer id, Model model) {
		GiftPhoto giftPhoto = giftPhotoPhotoService.getGiftPhoto(id);
		model.addAttribute("giftPhoto", giftPhoto);
		return "modules/gift/giftPhotoFormView";
	}
	
	
	
	@RequiresPermissions(value={"cms:giftPhoto:edit"})
	@RequestMapping(value = "save")
	public String save(@ModelAttribute GiftPhoto giftPhoto, HttpServletRequest request, Model model,  RedirectAttributes redirectAttributes) {
		int num = giftPhotoPhotoService.saveGiftPhoto(giftPhoto);
		if (num > 0) {
			addMessage(redirectAttributes, "保存成功");
		}else{
			addMessage(redirectAttributes, "保存失败");
		}
		return "redirect:" + adminPath + "/cms/giftPhoto/list?repage";
	}
	
	
	@RequiresPermissions("cms:giftPhoto:del")
	@RequestMapping(value = "delete")
	public String delete(@RequestParam Integer id, RedirectAttributes redirectAttributes) {
		if(id != null && id > 0){
			int num = giftPhotoPhotoService.deleteGiftPhoto(id);
			if (num > 0) {
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		}
		return "redirect:" + adminPath + "/cms/giftPhoto/list?repage";
	}
	
	/**
	 * 批量删除礼品图片
	 */
	@RequiresPermissions("cms:giftPhoto:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		if (StringUtils.isNotBlank(ids)){
			List<Integer> lds = new ArrayList<Integer>();
			String idArray[] =ids.split(",");
			for(String id : idArray){
				lds.add(Integer.valueOf(id));
			}
			
			int num = giftPhotoPhotoService.deleteMultiGiftPhotos(lds);
			if (num > 0) {
				addMessage(redirectAttributes, "删除成功");
			}else{
				addMessage(redirectAttributes, "删除失败");
			}
		}
		
		return "redirect:" + adminPath + "/cms/giftPhoto/list?repage";
		
	}
	
	
	
	
	
	
	
	
}
