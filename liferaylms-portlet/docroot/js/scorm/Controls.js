Type.createNamespace('ControlsCollection');
ControlsCollection.TreeNodeEventArgs = function(node) {
	ControlsCollection.TreeNodeEventArgs.constructBase(this);
	this.$1_0 = node;
}
ControlsCollection.TreeNodeEventArgs.prototype = {
	$1_0 : null,
	get_node : function() {
		return this.$1_0;
	}
}
ControlsCollection.TreeNode = function(domElement) {
	this.$2_4 = [];
	ControlsCollection.TreeNode.constructBase(this, [ domElement ]);
}
ControlsCollection.TreeNode.prototype = {
	$2_0 : null,
	$2_1 : null,
	$2_2 : null,
	$2_3 : null,
	getText : function() {
		if (Type.canCast(this, ControlsCollection.TreeView)) {
			return null;
		}
		return this.get_domElement().firstChild.lastChild.lastChild.children[1].innerText
				.trim();
	},
	getUrl : function() {
		return this.$2_3;
	},
	setUrl : function(url) {
		this.$2_3 = url;
	},
	getIcon : function() {
		if (Type.canCast(this, ControlsCollection.TreeView)) {
			return null;
		}
		return this.get_domElement().firstChild.lastChild.lastChild.firstChild;
	},
	getAnchor : function() {
		if (Type.canCast(this, ControlsCollection.TreeView)) {
			return null;
		}
		return this.get_domElement().firstChild.lastChild.lastChild
				.getElementsByTagName('a')[0];
	},
	setParent : function(parent) {
		this.$2_2 = parent;
	},
	getParent : function() {
		return this.$2_2;
	},
	getRoot : function() {
		if (this.$2_2 == null) {
			return (Type.canCast(this, ControlsCollection.TreeView)) ? this
					: null;
		} else {
			return this.$2_2.getRoot();
		}
	},
	getChildrens : function() {
		return this.$2_4;
	},
	addNode : function(text, href, iconSrc) {
		if (this.$2_1 == null) {
			this.$2_1 = document.createElement('div');
			if (!isNullOrUndefined(this.get_domElement().firstChild)
					&& this.get_domElement().firstChild.tagName.toLowerCase() === 'tbody') {
				this.get_domElement().firstChild.lastChild.lastChild
						.appendChild(this.$2_1);
			} else {
				this.get_domElement().appendChild(this.$2_1);
			}
		}
		var $0 = document.createElement('table');
		$0.setAttribute('border', '0');
		$0.setAttribute('cellpadding', '2');
		$0.setAttribute('cellspacing', '2');
		var $1 = document.createElement('td');
		if (!(Type.canCast(this, ControlsCollection.TreeView))) {
			$1.setAttribute('style', 'width:10px;');
			$1.setAttribute('width', '10');
		}
		var $2 = document.createElement('td');
		var $3 = document.createElement('tr');
		$3.appendChild($1);
		$3.appendChild($2);
		var $4 = document.createElement('tbody');
		$4.appendChild($3);
		$0.appendChild($4);
		var $5 = document.createElement('img');
		$5.setAttribute('border', '0');
		if (!isNullOrUndefined(iconSrc)) {
			$5.src = iconSrc;
		} else if (!isNullOrUndefined(this.getRoot().getMinusIcon())) {
			$5.src = this.getRoot().getMinusIcon();
		}
		$2.appendChild($5);
		var $6 = document.createTextNode(' ');
		$2.appendChild($6);
		var $7 = document.createElement('a');
		if (!isNullOrUndefined(href)) {
			$7.href = href;
		}
		$7.innerHTML = text;
		$2.appendChild($7);
		this.$2_1.appendChild($0);
		var $8 = new ControlsCollection.TreeNode($0);
		$8.setParent(this);
		$8.setUrl(href);
		this.$2_4.add($8);
		return $8;
	},
	get_userData : function() {
		return this.$2_0;
	},
	set_userData : function(value) {
		this.$2_0 = value;
		return value;
	}
}
ControlsCollection.TreeView = function(domElement, minusIcon, plusIcon) {
	ControlsCollection.TreeView.constructBase(this, [ domElement ]);
	this.$2_7 = minusIcon;
	this.$2_8 = plusIcon;
	this.$2_6 = Delegate.create(this, this.$2_9);
	this.get_domElement().attachEvent('onclick', this.$2_6);
}
ControlsCollection.TreeView.prototype = {
	add_nodeClick : function(value) {
		this.$2_5 = Delegate.combine(this.$2_5, value);
	},
	remove_nodeClick : function(value) {
		this.$2_5 = Delegate.remove(this.$2_5, value);
	},
	$2_5 : null,
	$2_6 : null,
	$2_7 : null,
	$2_8 : null,
	dispose : function() {
		this.get_domElement().detachEvent('onclick', this.$2_6);
		ControlsCollection.TreeView.callBase(this, 'dispose');
	},
	getMinusIcon : function() {
		return this.$2_7;
	},
	getPlusIcon : function() {
		return this.$2_8;
	},
	$2_9 : function() {
		var $0 = window.event.srcElement;
		var $1 = null;
		if (!isNullOrUndefined($0)) {
			$1 = $0.tagName.toLowerCase();
		}
		if ($1 === 'a' || $1 === 'img') {
			var $2 = null;
			while (!isNullOrUndefined($0)) {
				$2 = ScriptFX.UI.Control.getControl($0);
				if (!isNullOrUndefined($2)) {
					break;
				}
				$0 = $0.parentNode;
			}
			if ($2 != null) {
				if ($1 === 'img') {
					var $3 = $2.get_domElement().firstChild.lastChild.lastChild.lastChild;
					if ($3.tagName.toLowerCase() === 'div') {
						if ($3.style.display === 'none') {
							if (!isNullOrUndefined(this.getMinusIcon())) {
								$2.getIcon().src = this.getRoot()
										.getMinusIcon();
							}
							$3.style.display = '';
						} else {
							if (!isNullOrUndefined(this.getPlusIcon())) {
								$2.getIcon().src = this.getRoot().getPlusIcon();
							}
							$3.style.display = 'none';
						}
					}
				} else {
					if (this.$2_5 != null) {
						window.event.cancelBubble = true;
						var $4 = new ControlsCollection.TreeNodeEventArgs($2);
						this.$2_5.invoke(this, $4);
					}
				}
			}
			if ($1 === 'a') {
				if (window.event.preventDefault) {
	         	 	window.event.preventDefault();
	         	} else {
	         		window.event.returnValue = false;
	         	}
			}
		}
	}
}
ControlsCollection.TreeNodeEventArgs.createClass(
		'ControlsCollection.TreeNodeEventArgs', EventArgs);
ControlsCollection.TreeNode.createClass('ControlsCollection.TreeNode',
		ScriptFX.UI.Control);
ControlsCollection.TreeView.createClass('ControlsCollection.TreeView',
		ControlsCollection.TreeNode);
// ---- Do not remove this footer ----
// This script was generated using Script# v0.5.5.0
// (http://projects.nikhilk.net/ScriptSharp)
// -----------------------------------
