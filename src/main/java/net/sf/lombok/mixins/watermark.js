/*

Copyright (C) 2009, Blaz Lipuscek

Permission is hereby granted, free of charge, to any person obtaining a
copy of this software and associated documentation files (the "Software"),
to deal in the Software without restriction, including without limitation
the rights to use, copy, modify, merge, publish, distribute, sublicense,
and/or sell copies of the Software, and to permit persons to whom the
Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.  IN NO EVENT SHALL
THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
DEALINGS IN THE SOFTWARE.

 */
var Watermark = Class.create({

    initialize: function(el, text, inForm) {
        this.el = $(el);
        this.watermarkEl = new Element('div', {
            'class': 'watermarked'
        }).update(text);
        //this.el.observe('focus', this.hide.bind(this));
        if(inForm) {
            this.el.observe('focus', function() {
                this.watermarkEl.setStyle({
                    'textAlign': 'right'
                });
            }.bind(this));
            this.el.observe('keydown', this.hide.bind(this));
            this.watermarkEl.setStyle({
                width: (this.el.getWidth() - (this.el.paddingLeft() * 2) - (this.el.marginLeft() * 2)) + 'px'
                });
        } else {
            this.el.observe('focus', this.hide.bind(this));
        }
        this.el.observe('blur', this.show.bind(this));
        this.el.observe('change', function() {
            if(this.canShow())
                this.show()
            else
                this.hide();
        }.bind(this));
        this.watermarkEl.setStyle({
            display: 'none',
            fontSize:   this.el.getStyle('fontSize'),
            fontFamily: this.el.getStyle('fontFamily'),
            lineHeight: this.el.getStyle('lineHeight'),
            paddingLeft: this.el.getStyle('paddingLeft'),
            paddingTop: this.el.getStyle('paddingTop'),
            marginLeft: this.el.getStyle('marginLeft'),
            marginTop: this.el.getStyle('marginTop')
        });
        this.watermarkEl.observe('click', function() {
            this.el.focus();
        }.bind(this));

        // Wrap relative container around watermark element.
        var wrapperEl = new Element('div', { 'style': 'position:relative;float:left' });
        wrapperEl.insert(this.watermarkEl);
        Element.wrap(this.el, wrapperEl)
        this.el.disabled = false;

        var form = this.el.up('form');
        if(Object.isElement(form)) {
            form.observe('reset', function() {
                this.show.bind(this).defer();
            }.bind(this));
        }
        // Show watermark if text is not empty.
        this.show();
    },

    show: function() {
        this.watermarkEl.setStyle({
            'textAlign': 'left'
        });
        if(this.canShow()) {
            this.el.setValue('');
            this.watermarkEl.show();
        }
    },

    hide: function() {
        if(this.isActivated()) {
            this.watermarkEl.hide();
        }
    },

    canShow: function() {
        return !this.isActivated() && this.el.getValue().blank();
    },

    isActivated: function() {
        return this.watermarkEl.visible();
    }
});


Element.addMethods( ['INPUT', 'TEXTAREA'],{
    watermark: function(element, text, forTabs) {
        var waterMark = element.retrieve('watermark');
        if(!waterMark) {
            waterMark = new Watermark(element, text, forTabs);
            element.store('watermark', waterMark);
        }
        return waterMark;
    },

    setValue: function(element, value) {
        element = $(element);
        var method = element.tagName.toLowerCase();
        Form.Element.Serializers[method](element, value);
        if(value && element.retrieve('watermark')) {
            element.retrieve('watermark').hide();
        }
        return element;
    }
});